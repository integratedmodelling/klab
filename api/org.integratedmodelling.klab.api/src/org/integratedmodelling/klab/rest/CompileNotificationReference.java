package org.integratedmodelling.klab.rest;

public class CompileNotificationReference {

	private String statementUrn = null;
	private String scopeName = null;
	private String namespaceId = null;
	private String message = null;
	private int firstLine = 0;
	private int lastLine = 0;
	private int startOffset = 0;
	private int endOffset = 0;
	private int level = 0;

	public String getStatementUrn() {
		return statementUrn;
	}

	public void setStatementUrn(String statementUrn) {
		this.statementUrn = statementUrn;
	}

	public String getNamespaceId() {
		return namespaceId;
	}

	public void setNamespaceId(String namespaceId) {
		this.namespaceId = namespaceId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(int firstLine) {
		this.firstLine = firstLine;
	}

	public int getLastLine() {
		return lastLine;
	}

	public void setLastLine(int lastLine) {
		this.lastLine = lastLine;
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "CompileNotificationReference [statementUrn=" + statementUrn + ", namespaceId=" + namespaceId
				+ ", message=" + message + ", firstLine=" + firstLine + ", level=" + level + "]";
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

}
