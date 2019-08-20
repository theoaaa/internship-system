package org.scau.internshipsystem.common.security;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JWTToken {
  private String username;
  private Date expireTime;

}
