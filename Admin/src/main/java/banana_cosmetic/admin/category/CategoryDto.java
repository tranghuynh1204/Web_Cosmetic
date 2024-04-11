package banana_cosmetic.admin.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private Long id;
    private String name;
    private Boolean hasChildren;
}
