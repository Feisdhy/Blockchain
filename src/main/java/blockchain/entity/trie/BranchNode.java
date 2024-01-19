package blockchain.entity.trie;

public class BranchNode extends Node {
    public Node[] children;

    public BranchNode() {
        children = new Node[16];
    }
}
