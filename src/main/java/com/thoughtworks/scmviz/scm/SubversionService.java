package com.thoughtworks.scmviz.scm;

import com.thoughtworks.scmviz.model.Commit;
import com.thoughtworks.scmviz.model.Repository;
import org.joda.time.DateTime;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.util.*;

public class SubversionService {

    public SubversionService() {
        DAVRepositoryFactory.setup();
    }

    public List<Commit> retrieveCommits(Repository repository) {
        Collection<SVNLogEntry> logEntries = retrieveLogEntries(repository);
        ArrayList<Commit> commitList = new ArrayList<Commit>();
        for (SVNLogEntry logEntry : logEntries) {
            String author = logEntry.getAuthor() == null ? Commit.ANONYMOUS : logEntry.getAuthor();
            commitList.add(new Commit(new DateTime(logEntry.getDate()), author));
        }
        return commitList;
    }

    protected Collection<SVNLogEntry> retrieveLogEntries(Repository repository) {

        String[] targetPaths = new String[]{""};
        boolean changedPath = true;
        Collection entries = null;
        long startRevision = 0;
        int revision = -1;
        boolean endRevision = true;

        try {
            SVNRepository svnrepo = DAVRepositoryFactory.create(SVNURL.parseURIEncoded(repository.getUrl()));
            if (repository.getUsername() != null) {
                svnrepo.setAuthenticationManager(SVNWCUtil.createDefaultAuthenticationManager(repository.getUsername(), repository.getPassword()));
            }
            return (Collection<SVNLogEntry>) svnrepo.log(targetPaths, entries, startRevision, revision, changedPath, endRevision);
        } catch (SVNException e) {
            throw new RuntimeException("Error retrieving Subversion log entries for repository '" + repository.getUsername() + "'", e);
        }
    }

}
