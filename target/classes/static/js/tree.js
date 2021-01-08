/*************************************vars****************************************/
api_call_node_detail = "", api_call_image = "", options = "", currentId = 0, parentName = "", res = "", response_image_node = "", image_response = "";

const endpoints = {
    nodes: "http://localhost:8088/nodes/",
    nodeInfo: "http://localhost:8088/", // add the id of the node
    nodeImage: "http://localhost:8088/image/"
}
const hrefs = {
    updateNode: "/updateMember/",
    deleteNode: "/delete/"
}

/***************************************functions**********************************/

function printChart(nodos, nodosSinTratar) {
    let element = "";
    if (document.body.contains(document.getElementById("tree")))
        element = document.getElementById("tree");
    else if (document.body.contains(document.getElementById("treee")))
        element = document.getElementById("treee");

    let chart = new OrgChart(element, {
        template: "mila",
        mouseScrool: OrgChart.action.none,
        nodeMouseClick: OrgChart.action.none,
        nodeBinding: {
            field_0: "name",
            img_0: "img"
        },
        nodes: nodos,
    });

    chart.on('click', function (sender, args) {
        let data = sender.get(args.node.id);
        api_call_node_detail = endpoints.nodeInfo.slice(0, (endpoints.nodeInfo.lastIndexOf('/') + 1))
        showNodeDetails();
        api_call_node_detail += data.id;
        getParentNameById(nodosSinTratar, data.pid)
        //nodosSinTratar
        getSpecificNodeDetails(nodosSinTratar);

    });
}

//
async function getSpecificNodeDetails(all_nodes) {

    const response_specific_node = await fetch(api_call_node_detail);
    const data_one_node = await response_specific_node.json();
    let api_coll_node_image = endpoints.nodeImage + data_one_node.nodeDetails.id;

    const response_specific_image_node = await fetch(api_coll_node_image);
    const image_result = await response_specific_image_node.json();

    printNodeDetails(data_one_node, image_result);

    $("#update_data_node").click(function () {
        printUpdateDataForm(data_one_node, all_nodes);
    });

    $("#remove-node").click(function () {
        deleteNode(data_one_node);
    })
}

function checkAliveOrDead() {
    $('input[type=radio][name=isDead]').on('change', function () {
        if ($(this).val() === "true")
            $(".deadDate_section").css("display", "block")
        else
            $(".deadDate_section").css("display", "none")
    })
}

function showNodeDetails() {
    if (document.body.contains(document.getElementById("container-tree")))
        element = document.getElementById("container-tree");
    else if (document.body.contains(document.getElementById("container-treee")))
        element = document.getElementById("container-treee");

    if (element.id === "container-treee")
        element.className = "col-sm-7 d-flex justify-content-center";
    else
        element.className = "col-sm-9";
    document.getElementById("sidenav").style.display = "block";
}

function hideNodeDetails() {
    document.getElementById("btn_close").onclick = function () {
        if (document.body.contains(document.getElementById("container-tree")))
            element = document.getElementById("container-tree");
        else if (document.body.contains(document.getElementById("container-treee")))
            element = document.getElementById("container-treee");

        if (element.id === "container-treee")
            element.className = "col-sm-7 d-flex justify-content-center";
        else
            element.className = "col-sm-12 d-flex justify-content-center";

        document.getElementById("sidenav").style.display = "none";
    }
}

function deleteNode(data) {
    let hrefDeleteNodeBtn = hrefs.deleteNode + data.id;
    $("#delete_form > form").attr('action', hrefDeleteNodeBtn);
}

function getParentNameById(data, id) {
    if (data.id === id)
        parentName = data.nodeDetails.name;

    for (let i = 0; i < data.childs.length; i++) {
        if (data.childs[i].id === id)
            parentName = data.childs[i].nodeDetails.name;

        if (data.childs[i].childs.length > 0)
            getParentNameById(data.childs[i], id)
    }
}
/*

async function printOptions(nodes, id_current_node, name) {
    let parent_detail;
    if (nodes.parent !== null) {

        let parent_api_call = endpoints.nodeInfo.slice(0, (endpoints.nodeInfo.lastIndexOf('/') + 1));
        parent_api_call += nodes.parent;
        let response_parent = await fetch(parent_api_call);
        parent_detail = await response_parent.json();

        let options = []
        console.log("length " + options.length)
        document.querySelectorAll('#parentNameeUpdate > option').forEach((option) => {
            if (options.includes(option.value)) {
                option.remove()
            } else if (option.value !== name) {
                options.push(option.value)
            }
        })

        if (nodes.nodeDetails.name !== name) {
            // console.log("name nodes " + nodes.parent + " nose  " + nodes.nodeDetails.name + " current name " + name);
            //var sb = document.querySelector("#parentNameeUpdate > option");

            if (parent_detail !== undefined) {
                $("#parentNameeUpdate").append($('<option>', {
                    value: nodes.nodeDetails.name,
                    text: nodes.nodeDetails.name + " Son of " + parent_detail.nodeDetails.name
                }));
            }
            /!* else {
                $("#parentNameeUpdate").append($('<option>', {
                    value: nodes.nodeDetails.id,
                    text: nodes.nodeDetails.name
                }));
            }*!/
        }
    } else {
        $("#parentNameeUpdate").append($('<option>', {
            value: nodes.nodeDetails.name,
            text: nodes.nodeDetails.name
        }));
    }

    for (let i = 0; i < nodes.childs.length; i++) {
        printOptions(nodes.childs[i], id_current_node, name)
    }

    console.log("length " + options.length)
}

*/

function printUpdateDataForm(data, all_nodes) {
    let actionUpdateForm = hrefs.updateNode + data.id
    $("#update_form > form").attr('action', actionUpdateForm);
    let var_ = "th:if = \"${node.nodeDetails.id} != " + data.id + "\"";
    //$("#update_form form #parentNamee option").attr(var_);
    $("#parentNameUpdate");
    var sb = document.querySelector("#parentNameeUpdate");
    //removeAll(sb);

    /*var optionValues =[];
    $('#parentNameeUpdate option').each(function(){
        if($.inArray(this.value, optionValues) >-1 ){
            $(this).remove()
        }else {
            optionValues.push(this.value);
        }
    })
*/
    //console.log("name before " + data.nodeDetails.name)
   // printOptions(all_nodes, data.id, data.nodeDetails.name);

    options = "";
    currentId = data.id
    if (data.nodeDetails.name != null)
        $("#name__for__update").val(data.nodeDetails.name);
    else
        $("#name__for__update").val("We don't have this information yet.");
    if (data.nodeDetails.surname)
        $("#surname__for__update").val(data.nodeDetails.surname);
    else
        $("#surname__for__update").val("We don't have this information yet.");
    if (data.nodeDetails.birthDate != null)
        $("#birthDate__for__update").val(data.nodeDetails.birthDate);
    else
        $("#birthDate__for__update").val("We don't have this information yet.");
    if (data.nodeDetails.birthPlace != null)
        $("#birthPlace__for__update").val(data.nodeDetails.birthPlace);
    else
        $("#birthPlace__for__update").val("We don't have this information yet.");

    if (data.parent !== null) {
       // console.log("id for rlement " + data.id + " name " + data.nodeDetails.name)
        $('#parentNameeUpdate option[value="' + data.id + '"]').hide();
    }
    else
        $("#parent__for_update").val(data.nodeDetails.name + " is the root of the Tree.");
    if (data.nodeDetails.description != null)
        $("#description__for__update").val(data.nodeDetails.description);
    else
        $("#description__for__update").val("We don't have this information yet.");
}

function printNodeDetails(data, image_data) {

    if (data.nodeDetails.name != null)
        $(".node-name").text(data.nodeDetails.name);
    else
        $(".node-name").text("We don't have this information yet.");
    if (data.nodeDetails.surname)
        $(".node-surname").text(data.nodeDetails.surname);
    else
        $(".node-surname").text("We don't have this information yet.");
    if (data.nodeDetails.birthDate != null)
        $(".node-birth-date").text(data.nodeDetails.birthDate);
    else
        $(".node-birth-date").text("We don't have this information yet.");
    if (data.nodeDetails.birthPlace != null)
        $(".node-birth-place").text(data.nodeDetails.birthPlace);
    else
        $(".node-birth-place").text("We don't have this information yet.");
    if (image_data.image.length > 4) {
        $("#thumbnail").attr('src', image_data.image);
    } else
        $("#thumbnail").attr('src', "http://localhost:8088/images/profile_image.png");
    if (data.nodeDetails.deadDate != null) {
        $(".node-alive-dead").text("No");
        $(".node-death-date").text(data.nodeDetails.deadDate);
    } else {
        $(".node-alive-dead").text("Yes");
        $(".node-death-date").text("---");
    }
    if (data.parent !== null) {
        $(".node-parent-name").text(parentName);
    } else
        $(".node-parent-name").text(data.nodeDetails.name + " is the root of the Tree.");
    if (data.nodeDetails.description != null)
        $(".node-description").text(data.nodeDetails.description);
    else
        $(".node-description").text("We don't have this information yet.");
}

async function editImg(data) {
    try {
        api_call_image = endpoints.nodeImage + data.nodeDetails.id;
        response_image_node = await fetch(api_call_image);
        image_response = await response_image_node.json();
        let api_call_node = '', response_node = '', node_detail = '';

        if (data.parent !== null) {
            api_call_node = endpoints.nodeInfo.slice(0, (endpoints.nodeInfo.lastIndexOf('/') + 1));
            api_call_node += data.parent;
            response_node = await fetch(api_call_node);
            node_detail = await response_node.json();
        } else {
            api_call_node = endpoints.nodeInfo.slice(0, (endpoints.nodeInfo.lastIndexOf('/') + 1));
            api_call_node += data.id;
            response_node = await fetch(api_call_node);
            node_detail = await response_node.json();
        }

        if (image_response.image.length > 4) {
            if (data.parent === null)
                res += "[{id : " + data.id + ", name : \"" + data.nodeDetails.name + "\", img : \"" + image_response.image + "\"},";
            else
                res += "{id : " + data.id + ", pid : " + data.parent + ", name : \"" + data.nodeDetails.name + " Son of " + node_detail.nodeDetails.name + " \", img :\"" + image_response.image + "\"},";
        } else {
            if (data.parent === null)
                res += "[{id : " + data.id + ", name : \"" + data.nodeDetails.name + "\", img : \"http://localhost:8088/images/profile_image.png\"},";
            else
                res += "{id : " + data.id + ", pid : " + data.parent + ", name : \"" + data.nodeDetails.name + " Son of " + node_detail.nodeDetails.name + " \", img : \"http://localhost:8088/images/profile_image.png\"},";
        }
        for (let i = 0; i < data.childs.length; i++) {
            await editImg(data.childs[i]);
        }
        return res;
    } catch (error) {
        console.log('Error fetching and parsing data', error);
    }
}

//main
async function main() {

    const response_nodes = await fetch(endpoints.nodes)
    const all_nodes = await response_nodes.json();
    let treated_data = await editImg(all_nodes)
    treated_data = treated_data.slice(0, -1);
    treated_data += "]";
    console.log("********")
    console.log(treated_data)

    hideNodeDetails();
    printChart(eval(treated_data), all_nodes);
    checkAliveOrDead()
}

main().catch(err =>
    console.error(err));