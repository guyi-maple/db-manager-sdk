package top.guyi.db.manager.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbPage<T> {

    private int page;
    private int size;
    private long total;
    private Integer pageCount;
    private Collection<T> content;

    public int getPageCount(){
        if (this.pageCount == null){
            this.pageCount = (int)(this.total / this.size);
            if (this.total % this.size != 0){
                this.pageCount++;
            }
        }
        return this.pageCount;
    }

}
