/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 Core JavaScript functionality for the application.  Performs the required
 Restful calls, validates return values, and populates the member table.
 */
var table;
var tableData = {};
/* Builds the updated table for the member list */
function buildMemberRows(members) {
    return _.template($("#member-tmpl").html(), {"members": members});
}

/* Uses JAX-RS GET to retrieve current member list */
function updateMemberTable() {
    table.ajax.reload();
}

/*
 Attempts to register a new member using a JAX-RS POST.  The callbacks
 the refresh the member table, or process JAX-RS response codes to update
 the validation errors.
 */
function registerMember(memberData) {
    //clear existing  msgs
    $('span.invalid').remove();
    $('span.success').remove();

    // Display the loader widget
    //$.mobile.loading("show");

    $.ajax({
        url: 'rest/carsonparkings',
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        data: JSON.stringify(memberData),
        success: function (data) {
            //console.log("Member registered");

            //clear input fields
            $('#reg')[0].reset();

            //mark success on the registration form
            $('#formMsgs').append($('<span class="success">Машина принята</span>'));
            getFreeSeats($('#freeSeats'));
            updateMemberTable();
        },
        error: function (error) {
            if ((error.status == 409) || (error.status == 400)) {
                //console.log("Validation error registering user!");

                var errorMsg = $.parseJSON(error.responseText);

                $.each(errorMsg, function (index, val) {
                    $('<span class="invalid">' + val + '</span>').insertAfter($('#' + index));
                });
            } else {
                //console.log("error - unknown server issue");
                $('#formMsgs').append($('<span class="invalid">Unknown server error</span>'));
            }
        },
        complete: function () {
            // Hide the loader widget
            $.mobile.loading("hide");
        }
    });
}

/**
 * Ссылка на действие
 * @returns html a
 */
function addLink(cls, href, text) {
    return '<a class="' + cls + '" href="' + href + '">' + text + '</a>';
}
/**
 * Отпустить машину со стоянки
 * @param {type} obj
 * @returns {undefined}
 */
function releaseCar(obj) {
    $('span.invalid').remove();
    $('span.success').remove();
    var id = obj.href.split('/').pop();
    var data = tableData[id];
    $.ajax({
        url: '/restTest/rest/carsonparkings/release',
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        data: JSON.stringify(data),
        success: function (data) {

            $('#tableMsgs').append($('<span class="success">Машина отпущена со стоянки</span>'));


            getFreeSeats($('#freeSeats'));
            updateMemberTable();
        },
        error: function (error) {

            $('#tableMsgs').append($('<span class="invalid">' + error.responseText + '</span>'));

        },
        complete: function () {
            // Hide the loader widget
            $.mobile.loading("hide");
        }
    });
}



function getFreeSeats(jQobj) {
    $.ajax({
        url: '/restTest/rest/statisticInfo/freeSeats',
        contentType: "application/json",
        dataType: "json",
        type: "GET",        
        success: function (data) {

            jQobj.text(data);
        },
        error: function (error) {

            $('#tableMsgs').append($('<span class="invalid">' + error.responseText + '</span>'));

        },
        complete: function () {
            
        }
    });
}
