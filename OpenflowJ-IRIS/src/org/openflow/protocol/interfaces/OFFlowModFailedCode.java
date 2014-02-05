package org.openflow.protocol.interfaces;

public enum OFFlowModFailedCode {
	TABLE_FULL,
	EPERM,
	UNKNOWN,
	BAD_EMERG_TIMEOUT,
	BAD_TABLE_ID,
	UNSUPPORTED,
	ALL_TABLES_FULL,
	BAD_COMMAND,
	OVERLAP,
	BAD_FLAGS,
	BAD_TIMEOUT
}