function inArrayPos(arr, el)
{
    for (var index = 0; index < arr.length; index++)
    {
        if (arr[index] == el)
        {
            return index;
        }
    }
}

function getDetailText(name, url)
{
    var iframe = document.createElement("iframe");
    if (iframe.addEventListener)
    {
        iframe.addEventListener('load', function()
        {
            showDetailTree(this.name, this.contentDocument.body.firstChild.innerHTML);
            document.body.removeChild(this);
        });
    }
    else
    {
        iframe.onreadystatechange = function()
        {
            if ('complete' == this.readyState)
            {
                showDetailTree(this.name, this.contentDocument.body.firstChild.innerHTML);
                document.body.removeChild(this);
            }
        };
    }
    iframe.src = url;
    iframe.name = name;
    document.body.appendChild(iframe);
}

function showDetailTree(name, text)
{
	text = text.replace(/</g, "&lt;").replace(/>/g, "&gt;");
    var tree = text2Tree(text, "    ");
    tree.text = name;
    var rootNode = new Ext.tree.AsyncTreeNode(tree);
    
    var tree = new Ext.tree.TreePanel({
        id : 'tree',
        title : 'tree',
        region : 'center',
        root : rootNode,
        autoScroll : true,
        animate : false,
        header : false,
        rootVisible : true,
        listeners :
        {
            afterlayout : function(t)
            {
                t.expandAll();
            }
        }
    });
    
    var treeWin = new Ext.Window({
        title : name,
        width : 1024,
        height : 600,
        modal : true,
        maximizable : true,
        constrain : true,
        layout : 'fit',
        closeAction : "close",
        items:tree
    });
    treeWin.show();
}

function text2Tree(text, indent)
{
    var nodeTextArray = text.split(/\r?\n/g);
    var root = {id:0, name:"root", text:"root", children:[]};//new TreeNode(null, 0, "root", "root");
    var nodeLevelList = new Array(30);//new TreeNode[MAX_LEVEL];
    
    var level = 0;
    var nodeId = 0;
    nodeLevelList[level] = root;
    
    for (var index = 0; index < nodeTextArray.length; index++)
    {
        var nodeText = nodeTextArray[index];
        //for (var nodeText : nodeTextArray)
        //{
        if (nodeText.trim().length == 0)
        {
            continue;
        }
        
        level = 0;
        while (nodeText.search(indent) == 0)
        {
            level++;
            if (nodeLevelList[level] == null)
            {
                //nodeLevelList[level] = new TreeNode(
                //        nodeLevelList[level - 1], ++nodeId, "", "");
                nodeLevelList[level] = {id:++nodeId, name:"", text:"", children:[]};
                nodeLevelList[level - 1].children.push(nodeLevelList[level]);
            }
            
            nodeText = nodeText.replace(indent, "");
        }
        
        //nodeLevelList[level + 1] = new TreeNode(nodeLevelList[level],
        //        ++nodeId, nodeText, nodeText);
        nodeLevelList[level + 1] = {id:++nodeId, name:nodeText, text:nodeText, children:[]};
        nodeLevelList[level].children.push(nodeLevelList[level + 1]);
    }
    
    return root;
}