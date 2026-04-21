package mini_.projek_guna.Exception;

import mini_.projek_guna.response.WebResponse;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(ValidasiException.class)
    public ResponseEntity<WebResponse<String>> handleNotFound(ValidasiException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<String>builder()
                        .status("Fail")
                        .message(e.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<List<String>>> handleValidation(MethodArgumentNotValidException e){
        List<String> errorMessage = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(WebResponse.<List<String>>builder()
                        .status("Fail")
                        .message("Input tidak valid")
                        .data(errorMessage)
                        .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<WebResponse<String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(WebResponse.<String>builder()
                        .status("Error")
                        .message(e.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<WebResponse<String>> handleOptimisticLocking(OptimisticLockingFailureException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(WebResponse.<String>builder()
                        .status("Error")
                        .message("Gagal menyimpan, data telah diubah pengguna lain. Silakan muat ulang data.")
                        .data(null)
                        .build());
    }
}
