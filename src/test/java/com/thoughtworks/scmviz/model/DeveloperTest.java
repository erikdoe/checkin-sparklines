package com.thoughtworks.scmviz.model;

import com.thoughtworks.scmviz.model.Commit;
import com.thoughtworks.scmviz.model.Developer;
import org.joda.time.DateMidnight;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DeveloperTest {

    private Developer developer;

    @Before
    public void setup() {
        ArrayList<Commit> commits = new ArrayList<Commit>() {{
            add(new Commit(new DateMidnight(2010, 4, 19), "alice"));
            add(new Commit(new DateMidnight(2010, 5, 17), "alice"));
            add(new Commit(new DateMidnight(2010, 5, 18), "alice"));
        }};
        developer = new Developer("alice", commits);
    }

    @Test
    public void shouldReturnNumberOfCommits() {
        assertThat(developer.getNumberOfCommits(), equalTo(3));
    }

    @Test
    public void shouldReturnNumberOfRecentCommits() {
        DateTimeUtils.setCurrentMillisFixed(new DateMidnight(2010, 5, 19).getMillis());
        assertThat(developer.getNumberOfRecentCommits(), equalTo(2));
        DateTimeUtils.setCurrentMillisSystem();
    }

    @Test
    public void shouldReturnCommitCountsByMonth() {
        Map<LocalDate, Integer> result = developer.getNumberOfCommitsByMonth();

        assertThat(result.get(new LocalDate(2010, 4, 1)), equalTo(1));
        assertThat(result.get(new LocalDate(2010, 5, 1)), equalTo(2));
    }
}
