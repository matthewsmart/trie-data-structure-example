package com.example.trie;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class NodeTest {

    @Test
    public void willAddValue() {
        // given
        Node rootNode = new Node();
        assertThat(rootNode.getValues().size(), equalTo(0));

        // when
        Node node = rootNode.add("java");

        // then
        assertThat(node.getValue(), equalToIgnoringCase("Java"));
        assertThat(rootNode.get("java"), is(notNullValue()));
        assertThat(rootNode.getValues().size(), equalTo(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void willThrowExceptionWhenValueContainsNonAlphaChars() {
        // given
        Node rootNode = new Node();

        // when
        rootNode.add("j4va");

        // then
        // throws exception
    }

    @Test
    public void willRemoveValue() {
        // given
        Node rootNode = new Node();
        rootNode.add("java");
        rootNode.add("javascript");
        rootNode.add("swift");
        rootNode.add("python");
        assertThat(rootNode.get("javascript").getValue(), is(notNullValue()));
        assertThat(rootNode.getValues().size(), equalTo(4));

        // when
        rootNode.delete("javascript");

        // then
        assertThat(rootNode.get("javascript").getValue(), is(nullValue()));

        assertThat(rootNode.get("java").getValue(), is(notNullValue()));
        assertThat(rootNode.get("swift").getValue(), is(notNullValue()));
        assertThat(rootNode.get("python").getValue(), is(notNullValue()));
        assertThat(rootNode.getValues().size(), equalTo(3));
    }

    @Test
    public void willGetSubValues() {
        // given
        Node rootNode = new Node();
        rootNode.add("java");
        rootNode.add("javascript");
        rootNode.add("swift");
        rootNode.add("python");
        assertThat(rootNode.get("JavaScript").getValue(), is(notNullValue()));

        // when
        Node subNode = rootNode.get("Ja");

        // then
        assertThat(subNode.getValues(), contains("java", "javascript"));
        assertThat(subNode.getValues().size(), equalTo(2));
    }
}