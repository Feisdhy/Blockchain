package blockchain.entity.trie_trash;

public enum NodeType {
	BLANK(false),
	HASH(false),
	LEAF(true),
	EXTENSION(true),
	BRANCH(false);
	
	private boolean isKeyValueType;
	NodeType (boolean isKeyValueType) {
		this.isKeyValueType = isKeyValueType;
	}
	
	public boolean isKeyValueType() {
		return isKeyValueType;
	}
}
