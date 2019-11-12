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

import java.io.Serializable;
import java.util.UUID;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.terawarehouse.business.domain.catalog.CategoryDto;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@FeignClient(value = "catalog", path = "/catalog/categories")
public interface CatalogControllerProxy {

    @GetMapping(path = "/test/hello")
    public String test();

    @PostMapping(path = "/{pcid}")
    ResponseEntity<CategoryDto> createCategory(@PathVariable @NotNull UUID pcid, @RequestBody @Valid CategoryDto dto) throws NotSupportedException;

    @PutMapping(path = "/{uid}")
    ResponseEntity<CategoryDto> update(@RequestBody CategoryDto newDto, @PathVariable @NotNull Serializable uid) throws NotSupportedException;

    @PostMapping(path = "/{uid}/createOrUpdate")
    ResponseEntity<?> createOrUpdate(@RequestBody @Valid CategoryDto newDto, @NotNull @PathVariable("uuid") Serializable uid) throws NotSupportedException;

    @GetMapping(path = "/{uid}")
    EntityModel<CategoryDto> findById(@PathVariable UUID uid) throws NotSupportedException;

    @GetMapping(path = "/")
    CollectionModel<EntityModel<CategoryDto>> findAllCategories(Integer size, Integer page);

    @DeleteMapping(path = "/{uid}")
    public ResponseEntity<CategoryDto> delete(@PathVariable @NotNull Serializable uid) throws NotSupportedException;

}
