package com.paymybuddy.paymybuddy.dto;

import com.paymybuddy.paymybuddy.entity.Connection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePageDto {
    private String userConnected;
    private String solde;
    private ConnectionDto connectionForm;
    private Page<Connection> connections;
    private List<Integer> pageNumbers;
    private Integer currentPage = 1;
    private Integer totalPage = 1;
}
