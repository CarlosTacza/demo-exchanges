package com.everis.exchange.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table
public class Exchange {

    @Id
    @JsonIgnore
    private Long id;

    @NotNull(message = "El parámetro buy no puede ser nulo.")
    @ApiModelProperty(
            value = "Buy",
            name = "buy",
            dataType = "Double",
            example = "3.52")
    private Double buy;

    @NotNull(message = "El parámetro sell no puede ser nulo.")
    @ApiModelProperty(
            value = "Sell",
            name = "sell",
            dataType = "Double",
            example = "4.51")
    private Double sell;

    @NotNull(message = "El parámetro date no puede ser nulo.")
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @ApiModelProperty(
            value = "Date",
            name = "date",
            dataType = "LocalDateTime",
            example = "10-11-2020 09:45:30")
    private LocalDateTime date;
}
