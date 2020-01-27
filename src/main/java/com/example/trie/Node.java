package com.example.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {

    private String value;

    private final Node[] nodes = new Node[Alphabet.size()];

    public String getValue() {
        return value;
    }

    public Node add(final String value) {
        Objects.requireNonNull(value, "Value cannot be null");

        if (value.isEmpty() || !(value.matches("^[a-zA-Z]*$"))) {
            throw new IllegalArgumentException("Value must only contain alpha characters and have length greater than 0");
        }
        return add(value.toLowerCase(), 0);
    }

    public Node get(final String value) {
        Objects.requireNonNull(value, "Value cannot be null");

        return get(value.toLowerCase(), 0);
    }

    public void delete(final String value) {
        Objects.requireNonNull(value, "Value cannot be null");

        Node node = get(value);

        if (node != null) {
            node.setValue(null);
            // In future we may want to prune the tree structure after removing a node.
        }
    }

    public List<String> getValues() {
        List<String> values = new ArrayList<>();
        for (Node childNode : nodes) {
            if (childNode != null) {
                if (childNode.getValue() != null) {
                    values.add(childNode.getValue());
                }
                values.addAll(childNode.getValues());
            }
        }
        return values;
    }

    public String print() {
        StringBuilder string = new StringBuilder();
        string.append("<root>" + "\n");
        for (String line : getLines()) {
            string.append(line);
        }
        return string.toString();
    }

    private void setValue(final String value) {
        this.value = value;
    }

    private Node add(final String value, final int pos) {
        char character = value.charAt(pos);

        Node childNode = nodes[Alphabet.getPosition(character)];
        if (childNode == null) {
            childNode = new Node();
            nodes[Alphabet.getPosition(character)] = childNode;
        }
        if (pos == value.length() -1) {
            childNode.value = value;
            return childNode;
        } else {
            return childNode.add(value, pos + 1);
        }
    }

    private Node get(final String value, final int pos) {
        char character = value.charAt(pos);

        Node childNode = nodes[Alphabet.getPosition(character)];
        if (childNode == null) {
            return null;
        }
        if (pos >= (value.length() -1)) {
            return childNode;
        } else {
            return childNode.get(value, pos + 1);
        }
    }

    private List<String> getLines() {
        List<String> lines = new ArrayList<>();
        if (value != null) {
            lines.add("->" + value.toUpperCase() + "\n");
        }
        for (int i = 0; i < Alphabet.size(); i++) {
            if (nodes[i] != null) {
                lines.add("-" + Alphabet.getCharacter(i) + "\n");
                for (String line : nodes[i].getLines()) {
                    lines.add("-" + line);
                }
            }
        }
        return lines;
    }
}
