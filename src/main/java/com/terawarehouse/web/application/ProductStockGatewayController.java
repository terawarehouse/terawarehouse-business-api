/**
 * An Open Source Inventory and Sales Management System
 * Copyright (C) 2019 Edward P. Legaspi (https://github.com/czetsuya)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.terawarehouse.web.application;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.terawarehouse.business.domain.catalog.ProductDto;
import com.terawarehouse.business.domain.inventory.ProductStockDto;
import com.terawarehouse.gateway.controller.client.catalog.ProductServiceProxy;
import com.terawarehouse.gateway.controller.client.inventory.ProductStockQueryServiceProxy;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 * @since 0.0.1
 * @version 0.0.1
 */
@RestController
@RequestMapping(path = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductStockGatewayController {

    @Autowired
    private ProductServiceProxy productServiceProxy;

    @Autowired
    private ProductStockQueryServiceProxy productStockQueryService;

    @GetMapping(path = "/{productId}")
    public List<EntityModel<ProductStockDto>> findProductStocks(@PathVariable UUID productId, @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page) {

        CollectionModel<EntityModel<ProductStockDto>> productStocks = productStockQueryService.findAll(size, page);
        return productStocks.getContent().stream().map(e -> {
            ProductStockDto productStockDto = e.getContent();
            try {
                ProductDto productDto = productServiceProxy.findById(productStockDto.getProductId()).getContent();
                productStockDto.setProductCode(productDto.getCode());

            } catch (NotSupportedException e1) {
                return null;
            }

            return e;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
