/*************************************vars****************************************/
let parentName = "",
    res = "",
    response_image_node = "",
    image_response = "";

const endpoints = {
    nodes: "http://localhost:8088/api/nodes/",
    node: "http://localhost:8088/api/node/", // plus the id of the node
    nodeImage: "http://localhost:8088/image/",
    nodeDetails: "http://localhost:8088/api/nodeDetails/"
}

const hrefs = {
    updateNode: "/updateMember/",
    deleteNode: "/delete/"
}

/***************************************functions**********************************/
async function getSpecificNodeDetails(api_call_node_detail) {

    const response_specific_node = await fetch(api_call_node_detail);
    const data_one_node = await response_specific_node.json();
    let api_coll_node_image = endpoints.nodeImage + data_one_node.nodeDetails.id;

    const response_specific_image_node = await fetch(api_coll_node_image);
    const image_result = await response_specific_image_node.json();

    printNodeDetails(data_one_node, image_result);

    $("#update_data_node").click(function () {
        printUpdateDataForm(data_one_node);
    });

    $("#remove-node").click(function () {
        deleteNode(data_one_node);
    })
}

//hide the death date if it alive
//otherwise we show it
function checkAliveOrDead() {
    $('input[type=radio][name=isDead]').on('change', function () {
        if ($(this).val() === "true")
            $(".deadDate_section").css("display", "block")
        else
            $(".deadDate_section").css("display", "none")
    })
}

//control node details section (show or hide)
//and change the class to re-position the elements again
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

//control node details section (show)
//by activating the hide section btn
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

//delete the selected node by his id
function deleteNode(data) {
    let hrefDeleteNodeBtn = hrefs.deleteNode + data.id;
    $("#delete_form > form").attr('action', hrefDeleteNodeBtn);

    //in case de submit the delete form we show a notification
    // Refactor
    $("#remove_node").on("submit", function () {
        localStorage.setItem("notification", "Deleted successfully");
        console.log("submit delete ", localStorage.getItem("notification"))
    });
}

//make request to get the father name by his id details
async function getFatherNameById(id) {
    let nodeDetailsId = (id+1);
    const  call_parent_name_endpoint = endpoints.nodeDetails + nodeDetailsId;
    const request_parent_name = await fetch(call_parent_name_endpoint);

    if (request_parent_name.ok) {
        const response = await request_parent_name.json();
        parentName = response.name
    }
}

//fill the fields of update modal form with data
function printUpdateDataForm(data) {
    let nodeInfo = data.nodeDetails;
    let actionUpdateForm = hrefs.updateNode + data.id;

        $("#update_form > form").attr('action', actionUpdateForm);

    for (let key of Object.keys(nodeInfo)){
        let $updateInfoSection = jQuery(`#${key}__for__update`);
        $updateInfoSection.val(nodeInfo[key]);
    }

    if (data.parent === null) {
        $(".parent__name").css('display', 'none');
    }else {
        $(`#parentNameUpdate option[value="${data.id}"]`).hide();
        $(".parent__name").css('display', 'block');
    }
}

function printNodeDetails(data, image_data) {
    let defaultPathImage = 'http://localhost:8088/images/profile_image.png';
    let nodeInfo = data.nodeDetails;
    let $imgSection = jQuery('#thumbnail');
    let $parentNameSection = jQuery('.node-parent-name');
    let defaultMsg = "We don't have this information yet.";

    for (let key of Object.keys(nodeInfo)) {
        let $infoSection = jQuery(`.node-${key}`);

        //if we had info of the selected member we show them otherwise we show a default msg
        (nodeInfo[key] && key !== 'dead') ? $infoSection.text(nodeInfo[key]) : $infoSection.text(defaultMsg);

        //in case if it dead we show the death Date
        //Otherwise we hide it
        if (key === 'dead' && nodeInfo[key]) {
            $(".node-alive-dead").text("No");
            $(".node-deadDate").text(nodeInfo['deadDate']);
        } else if (key === 'dead' && !nodeInfo[key]) {
            $(".node-alive-dead").text("Yes");
            $(".node-deadDate").text("---");
        }
    }
    //shows image of the member if it exists if not we add a default img
    !image_data.image.includes("null") ? $imgSection.attr('src', image_data.image) : $imgSection.attr('src', defaultPathImage);

    //shows the name parent of the member if it has otherwise we show a default msg
    data.parent ? $parentNameSection.text(parentName) : $parentNameSection.text(nodeInfo.name + " is the root of the Tree.");
}

async function editImg(data) {
    try {
        let api_call_image = endpoints.nodeImage + data.nodeDetails.id;
        response_image_node = await fetch(api_call_image);
        image_response = await response_image_node.json();
        let api_call_node = '', response_node = '', node_detail = '';

        api_call_node = endpoints.node;
        if (data.parent !== null)
            api_call_node += data.parent;
        else
            api_call_node += data.id;

        response_node = await fetch(api_call_node);
        node_detail = await response_node.json();

        if (image_response.image.includes("null"))
            image_response.image = "http://localhost:8088/images/profile_image.png";

        if (data.parent === null)
            res += "[{id : " + data.id + ", name : \"" + data.nodeDetails.name + "\", img : \"" + image_response.image + "\"},";
        else
            res += "{id : " + data.id + ", pid : " + data.parent + ", name : \"" + data.nodeDetails.name + " Son of " + node_detail.nodeDetails.name + " \", img :\"" + image_response.image + "\"},";

        for (let i = 0; i < data.childs.length; i++) {
            await editImg(data.childs[i]);
        }
        return res;
    } catch
        (error) {
        console.log('Error fetching and parsing data', error);
    }
}

//main
async function main() {


    //to activate the checkAliveOrDead method
    checkAliveOrDead();
    //to activate the hideNodeDetails method
    hideNodeDetails();

    //making the request consulting all the nodes that we have created
    const request_nodes = await fetch(endpoints.nodes)

    //if the request is success
    if (request_nodes.ok) {
        try {
            const all_nodes = await request_nodes.json();
            let treated_data = await editImg(all_nodes);
            //evaluating the response to give it a recognized json format
            treated_data = eval(treated_data.slice(0, -1) + "]");

            //printing the tree chart of the family
            let chart = printChart(treated_data, all_nodes);

            //activating the click on any node of the tree
            chart.on('click', function (sender, args) {
                let data = sender.get(args.node.id);
                let api_call_node_detail = endpoints.node + data.id;

                //we show the member details section
                showNodeDetails();

                //to get the father name
                if (data.pid)
                    getFatherNameById(data.pid)

                //er filled the member details section with the member information
                getSpecificNodeDetails(api_call_node_detail);
            });
        }catch (e) {
            console.log("No data to print")
        }
    }
}

main().catch(err =>
    console.error(err));