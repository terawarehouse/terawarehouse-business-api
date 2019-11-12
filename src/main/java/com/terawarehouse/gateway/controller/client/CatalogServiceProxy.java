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
package com.terawarehouse.gateway.controller.client;

import java.util.UUID;

import javax.transaction.NotSupportedException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.terawarehouse.business.domain.catalog.CategoryDto;
import com.terawarehouse.business.domain.catalog.ProductDto;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@FeignClient(value = "catalog")
public interface CatalogServiceProxy {

    @GetMapping(path = "/catalog/categories")
    CollectionModel<EntityModel<CategoryDto>> findAllCategories(@RequestParam(required = false) Integer size, @RequestParam(required = false) Integer page);

    @GetMapping(path = "/catalog/products/{uid}")
    public EntityModel<ProductDto> findById(@PathVariable UUID uid) throws NotSupportedException;

}
