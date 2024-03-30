package hello.itemservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ItemParamDto {

    private String itemName;
    private Integer price;
    private Integer quantity;
}
