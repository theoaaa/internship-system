package org.scau.internshipsystem.system.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PermOfRoleParam {
    @NotNull
    private Integer roleId;
    @NotEmpty
    private List<Integer> menuIds;
}
