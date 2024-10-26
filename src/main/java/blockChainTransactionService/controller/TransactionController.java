package blockChainTransactionService.controller;

import blockChainTransactionService.dto.TransactionDTO;
import blockChainTransactionService.service.ethereum.IEthereumTransactionSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final IEthereumTransactionSearchService transactionService;

    @Autowired
    public TransactionController(IEthereumTransactionSearchService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/search")
    @Operation(summary = "Search transactions", description = "Search for transactions based on various filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved transactions", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transactions not found", content = @Content)
    })
    public ResponseEntity<List<TransactionDTO>> searchTransactions(
            @Parameter(description = "Type of transaction", required = false) @RequestParam(required = false) String type,
            @Parameter(description = "Value associated with the transaction", required = false) @RequestParam(required = false) String value,
            @Parameter(description = "Block hash of the transaction", required = false) @RequestParam(required = false) String blockHash,
            @Parameter(description = "Transaction hash", required = false) @RequestParam(required = false) String hash,
            @Parameter(description = "Full-text search for transactions", required = false) @RequestParam(required = false) String fullSearch) {
        List<TransactionDTO> results = transactionService.searchTransactions(type, value, blockHash, hash, fullSearch);
        return ResponseEntity.ok(results);
    }
}
