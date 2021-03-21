
//print Tree chart with all members nodes
//shows the name and the img of the family member
function printChart(nodes) {
    let element = "";
    if (document.body.contains(document.getElementById("tree")))
        element = document.getElementById("tree");
    else if (document.body.contains(document.getElementById("treee")))
        element = document.getElementById("treee");

    OrgChart.templates.ana.field_0  = '<text width="230" style="font-size: 18px;" fill="#ffffff" x="125" y="95" text-anchor="middle" class="field_0">{val}</text>';
    OrgChart.templates.ana.field_1 =  '<text width="130" text-overflow="multiline" style="font-size: 14px;" fill="#ffffff" x="230" y="30" text-anchor="end" class="field_1">{val}</text>';

    return new OrgChart(element, {
        mouseScrool: OrgChart.action.none,
        nodeMouseClick: OrgChart.action.none,
        nodeBinding: {
            field_0: "name",
            img_0: "img"
        },
        nodes: nodes,
    });
}