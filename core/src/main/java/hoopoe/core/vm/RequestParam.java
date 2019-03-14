package hoopoe.core.vm;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class RequestParam implements Serializable {

    private int pageSize = 10;
    private int pageNum = 1;

    private String sortField;
    private String sortOrder;

}
