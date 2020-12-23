/*************************************vars****************************************/
let results = "", api_call_node_detail = "";
const endpoints = {
    nodes : "http://localhost:8088/nodes/",
    nodeInfo : "http://localhost:8088/" // add the id of the node
}

/***************************************functions**********************************/
function printChart(nodos, node_detail) {
    let element = "";
    if (document.body.contains(document.getElementById("tree")))
        element = document.getElementById("tree");
    else if (document.body.contains(document.getElementById("treee")))
        element = document.getElementById("treee");

    let chart = new OrgChart(element, {
        nodeMouseClick: OrgChart.action.none,
        mouseScrool: OrgChart.action.none,
        nodeMenu: {
            details: {text: "Details"},
            edit: {text: "Edit"},
            add: {text: "Add"},
            remove: {text: "Remove"}
        },
        nodeBinding: {
            field_0: "name"
        },
        nodes: nodos,
    });

    chart.on('click', function (sender, args) {
        let data = sender.get(args.node.id);
        api_call_node_detail = endpoints.nodeInfo.slice(0, (endpoints.nodeInfo.lastIndexOf('/')+1))
        showNodeDetails();
        api_call_node_detail += data.id;

        getSpecificNodeDetails();
    });
}

//
async function getSpecificNodeDetails(){

    const response_specific_node = await fetch(api_call_node_detail)
    const  data_one_node = await response_specific_node.json();
    printNodeDetails(data_one_node);
    /*console.log("Eee " + api_call_node_detail);
    console.log("/////////////////////////////")
    console.log(response_specific_node)
    console.log("/////////////////////////////")*/
}

function treatData(data){
    if (data.parent === null)
        results += "[{id : " + data.id + ", name : \"" + data.nodeDetails.name + "\"},";
    else
        results += "{id : " + data.id + ", pid : "  + data.parent + ", name : \"" + data.nodeDetails.name + "\"},";

    for (let i = 0; i < data.childs.length; i++) {
        //console.log(data.childs[i].id)
       if (data.childs[i].childs.length === 0)
            results += "{id : " + data.childs[i].id + ", pid : " + data.childs[i].parent + ", name : \"" + data.childs[i].nodeDetails.name + "\"},";

        if (data.childs[i].childs.length > 0){

            treatData(data.childs[i]);
        }
    }
}

function showNodeDetails(){
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

function hideNodeDetails(){
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

function printNodeDetails(data) {
    console.log(data.nodeDetails.birthPlace);

    if (data.nodeDetails.name !=null)
        document.getElementsByClassName("node-name")[0].textContent = data.nodeDetails.name;
    else
        document.getElementsByClassName("node-name")[0].textContent = "We don't have this information yet.";
    if (data.nodeDetails.surname)
        document.getElementsByClassName("node-surname")[0].textContent = data.nodeDetails.surname;
    else
        document.getElementsByClassName("node-surname")[0].textContent = "We don't have this information yet.";
    if (data.nodeDetails.birthDate != null)
        document.getElementsByClassName("node-birth-date")[0].textContent = data.nodeDetails.birthDate;
    else
        document.getElementsByClassName("node-birth-date")[0].textContent = "We don't have this information yet.";
    if (data.nodeDetails.birthPlace != null)
        document.getElementsByClassName("node-birth-place")[0].textContent = data.nodeDetails.birthPlace;
    else
        document.getElementsByClassName("node-birth-place")[0].textContent = "We don't have this information yet.";

    document.getElementsByClassName("node-death-date")[0].textContent = "We don't have this information yet.";

    if (data.parent !== null)
        document.getElementsByClassName("node-parent-name")[0].textContent = data.parent;
    else
        document.getElementsByClassName("node-parent-name")[0].textContent = data.nodeDetails.name + " is the root of the Tree.";
    if (data.nodeDetails.description != null)
        document.getElementsByClassName("node-description")[0].textContent = data.nodeDetails.description;
    else
        document.getElementsByClassName("node-description")[0].textContent = "We don't have this information yet.";
}

//main
async function main() {

    const response_nodes = await fetch(endpoints.nodes)
    const all_nodes = await response_nodes.json();
    //console.log(data)
    treatData(all_nodes);
    results = results.slice(0,-1);
    results += "]";
    //console.log(results);
    hideNodeDetails();
    const node_detail = "hola";
    printChart(eval(results) ,node_detail);

}

main().catch(err =>
    console.error(err));