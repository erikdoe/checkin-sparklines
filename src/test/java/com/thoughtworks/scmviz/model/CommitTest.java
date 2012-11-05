package com.thoughtworks.scmviz.model;


import com.thoughtworks.scmviz.model.Commit;
import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class CommitTest {

    private ArrayList<Commit> commits;

    @Before
    public void setup() {
        commits = new ArrayList<Commit>() {{
            add(new Commit(new DateMidnight(2011, 5, 18), "alice"));
            add(new Commit(new DateMidnight(2011, 5, 17), "alice"));
            add(new Commit(new DateMidnight(2011, 5, 18), "bob"));
        }};
    }

    @Test
    public void shouldFilterCommits() {

        List<Commit> result = Commit.filter(commits, new Commit.FilterFunction() {
            public boolean eval(Commit c) {
                return c.getCommitter().equals("alice");
            }
        });

        assertThat(result.get(0).getCommitter(), equalTo("alice"));
        assertThat(result.get(1).getCommitter(), equalTo("alice"));
        assertThat(result.get(1), not(equalTo(result.get(0))));
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void shouldGroupCommits() {

        Map<String, List<Commit>> result = Commit.group(commits, new Commit.GroupFunction<String>() {
            public String eval(Commit c) {
                return c.getCommitter();
            }
        });

        assertThat(result.get("alice").size(), equalTo(2));
        assertThat(result.get("bob").size(), equalTo(1));
        assertThat(result.size(), equalTo(2));
    }
}
