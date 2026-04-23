package com.example.llmdamo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_history")
public class History implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private  Long id;

    private LocalDateTime dataTime;

    private  Long sessionId;

    private  String content;

    private  String role;


}
