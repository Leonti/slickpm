package com.leonti.slickpm.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leonti.slickpm.domain.GitVcs;
import com.leonti.slickpm.domain.Task;
import com.leonti.slickpm.domain.Vcs;
import com.leonti.slickpm.domain.dto.VcsCommit;
import com.leonti.slickpm.domain.dto.VcsDiff;

@Service("VcsService")
@Transactional
public class VcsService {

	public static enum VcsStatus {OK, INVALID_URI}
	
    @Autowired
    private SessionFactory sessionFactory;	
 
    private String getRepoFolder(String id) {
    	try {
			Properties props = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
			String folder = props.getProperty("uploadDir");
			
			return folder + File.separator + "gitrepos" + File.separator + id;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
	public List<VcsCommit> getDiffForTask(GitVcs gitVcs, Task task) {
		
		List<VcsCommit> commits = new ArrayList<VcsCommit>(); 
		
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try {
						
			File gitPath = new File(getRepoFolder(String.valueOf(gitVcs.getId())) + File.separator + ".git");
			Repository repository = builder.setGitDir(gitPath)
					  .readEnvironment() // scan environment GIT_* variables
					  .findGitDir() // scan up the file system tree
					  .build();			
	
			Git git = new Git(repository);
			git.pull().call();
			Iterable<RevCommit> log = git.log().call();
	
			RevWalk rw = new RevWalk(repository);
			
			for (RevCommit commit : log) {
					
				if (commit.getFullMessage().contains("#" + task.getId() + " ")) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					DiffFormatter df = new DiffFormatter(out);
					df.setRepository(repository);
					df.setDiffComparator(RawTextComparator.DEFAULT);
					df.setDetectRenames(true);				
						
					List<DiffEntry> diffs = null;
					
					if (commit.getParentCount() > 0) {
						
						RevCommit parent = rw.parseCommit(commit.getParent(0).getId());
						
						diffs = df.scan(parent.getTree(), commit.getTree());
					} else {
						diffs = df.scan(new EmptyTreeIterator(),
						        new CanonicalTreeParser(null, rw.getObjectReader(), commit.getTree()));					
					}

					VcsCommit vcsCommit = new VcsCommit(commit.getName(), commit.getFullMessage());
					commits.add(vcsCommit);				
					for (DiffEntry diff : diffs) {
						
					    df.format(diff);				    
					    vcsCommit.addDiff(new VcsDiff(diff.getNewPath(), out.toString("UTF-8")));
					    
					    out.reset();						
					}					
				}
			}
			
			rw.release();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return commits;
	}
	
	public VcsStatus checkAndCreateVcsCache(GitVcs gitVcs) {
		
		String repoFolder = getRepoFolder(String.valueOf(gitVcs.getId()));
		File repoPath = new File(repoFolder);
		
		try {
			FileUtils.deleteDirectory(repoPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		repoPath.mkdirs();
		CloneCommand clone = Git.cloneRepository();
		clone.setBare(false);
		clone.setCloneAllBranches(true);
		clone.setDirectory(repoPath).setURI(gitVcs.getUri());
		
		try {
			clone.call();
		} catch (InvalidRemoteException e) {
			return VcsStatus.INVALID_URI;
		} catch (TransportException e) {
			return VcsStatus.INVALID_URI;
		} catch (GitAPIException e) {
			return VcsStatus.INVALID_URI;
		}						
				
		return VcsStatus.OK;
	}
	
	public Vcs save(GitVcs vcs) {
		sessionFactory.getCurrentSession().saveOrUpdate(vcs);
		return vcs;
	}	
    
    public void delete(GitVcs vcs) {
    	sessionFactory.getCurrentSession().delete(vcs);
    }
    
	public GitVcs getById(Integer id) {

    	return (GitVcs) sessionFactory.getCurrentSession()
    			.createQuery("FROM GitVcs WHERE id = ?")
    			.setLong(0, id)
    			.setMaxResults(1)
    			.uniqueResult();
	}    
}