package com.thoughtworks.scmviz.model;

import com.thoughtworks.scmviz.model.Commit;
import com.thoughtworks.scmviz.model.Developer;
import com.thoughtworks.scmviz.model.Project;
import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProjectTest {

    private Project project;

    @Before
    public void setup() {
        project = new Project(new Repository("test"));
        ArrayList<Commit> commits = new ArrayList<Commit>() {{
            add(new Commit(new DateMidnight(2011, 5, 18), "alice"));
            add(new Commit(new DateMidnight(2011, 5, 17), "alice"));
            add(new Commit(new DateMidnight(2011, 5, 18), "bob"));
        }};
        project.addCommits(commits);
    }

    @Test
    public void shouldReturnCommitter() {

        Developer developer = project.getDeveloper("alice");

        assertThat(developer.getName(), equalTo("alice"));
        assertThat(developer.getCommits().size(), equalTo(2));
    }

    @Test
    public void shouldReturnCommittersUniqueAndSorted() {

        List<Developer> result = project.getAllDevelopers();

        assertThat(result.get(0).getName(), equalTo("alice"));
        assertThat(result.get(0).getCommits().size(), equalTo(2));
        assertThat(result.get(1).getName(), equalTo("bob"));
        assertThat(result.size(), equalTo(2));
    }

}
