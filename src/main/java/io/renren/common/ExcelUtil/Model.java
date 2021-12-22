/**
 * @author SargerasWang
 */
package io.renren.common.ExcelUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The <code>Model</code>
 *
 * @author SargerasWang Created at 2014年8月7日 下午5:09:29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @ExcelCell(index = 0)
    private String a;
    @ExcelCell(index = 1)
    private String b;
    @ExcelCell(index = 2)
    private String c;
    @ExcelCell(index = 3)
    private String d;
    @ExcelCell(index = 4)
    private String e;
    @ExcelCell(index = 5)
    private String f;
    @ExcelCell(index = 6)
    private String g;
    @ExcelCell(index = 7)
    private String h;
    @ExcelCell(index = 8)
    private String i;
}
