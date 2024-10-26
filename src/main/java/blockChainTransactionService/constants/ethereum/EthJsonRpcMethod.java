package blockChainTransactionService.constants.ethereum;

public enum EthJsonRpcMethod {
    ETH_GET_BLOCK_BY_NUMBER("eth_getBlockByNumber"),
    ETH_GET_TRANSACTION_BY_HASH("eth_getTransactionByHash"),
    ETH_SEND_RAW_TRANSACTION("eth_sendRawTransaction"),
    ETH_CALL("eth_call"),
    ETH_GET_TRANSACTION_RECEIPT("eth_getTransactionReceipt");

    private final String methodName;

    EthJsonRpcMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
