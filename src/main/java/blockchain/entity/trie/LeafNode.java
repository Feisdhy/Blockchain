package blockchain.entity.trie;

public class LeafNode extends Node {
    public String key;
    public String value;

    public LeafNode() {

    }

    public LeafNode(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
