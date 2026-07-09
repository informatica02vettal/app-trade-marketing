package ve.com.vettal.trademarketing.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {

	private int status;
	private String message;
	private T data;
	private LocalDateTime timestamp;
	private String path;
	private Map<String, String> errors;

	public static <T> ApiResponseDto<T> ok(T data, String message) {
		return ApiResponseDto.<T>builder()
				.status(200)
				.message(message)
				.data(data)
				.timestamp(LocalDateTime.now())
				.build();
	}

	public static <T> ApiResponseDto<T> created(T data, String message) {
		return ApiResponseDto.<T>builder()
				.status(201)
				.message(message)
				.data(data)
				.timestamp(LocalDateTime.now())
				.build();
	}
}
