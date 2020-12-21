/**
 * 
 * 画笔类
 * 
 * @author  sKF47876
 * @version  [版本号, 2012-6-13]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
var Graphics = function()
{
	throw new Exception();
};

Graphics.UP_TO_DOWN = 0;
Graphics.LEFT_TO_RIGHT = 1;
Graphics.DOWN_TO_UP = 2;
Graphics.RIGHT_TO_LEFT = 3;

/**
	绘制水平、垂直线
	@param canvas
	@param startX
	@param startY
	@param long
	@param direction
	@param type
	@param size
	@param color
*/
Graphics.drawLine = function(canvas, startX, startY, long, direction, type, size, color)
{
	var lineDiv = document.createElement("div");
	lineDiv.style.position = "absolute";
	lineDiv.style.top = startY + "px";
	lineDiv.style.left = startX + "px";
	if (direction == Graphics.UP_TO_DOWN)
	{
		lineDiv.style.height = long + "px";
		lineDiv.style.borderLeft = size + "px " + type + " " + color;
	}
	else
	{
		lineDiv.style.width = long + "px";
		lineDiv.style.borderTop = size + "px " + type + " " + color;
	}
	
	canvas.appendChild(lineDiv);
	
	return lineDiv;
};

Graphics.drawCursor = function(canvas, x, y, direction, color)
{
	for (var index = 0; index < 5; index++)
	{
		var lineDiv1 = document.createElement("div");
		lineDiv1.style.position = "absolute";
		lineDiv1.style.width = "3px";
		lineDiv1.style.height = "3px";
		lineDiv1.style.background = color;
		if (direction == Graphics.LEFT_TO_RIGHT)
		{
			lineDiv1.style.left = x - index + "px";
		}
		else
		{
			lineDiv1.style.left = x + index + "px";
		}
		var lineDiv2 = lineDiv1.cloneNode(false);
		lineDiv1.style.top = y - index + "px";
		lineDiv2.style.top = y + index + "px";
		canvas.appendChild(lineDiv1);
		canvas.appendChild(lineDiv2);
	}
};

/**
	绘制文本
	@param canvas
	@param posX
	@param posY
	@param text
	@param size
	@param color
*/
Graphics.drawText = function(canvas, posX, posY, text, size, color, url, dblclick, msg)
{
	var textNode = document.createElement("span");
	textNode.appendChild(document.createTextNode(text));
	textNode.style.color = color;
	textNode.style.fontSize = size + "px";
	textNode.style.fontFamily = "Arial, Helvetica, sans-serif";
	if (dblclick != null)
	{
		textNode.ondblclick = function(){dblclick(msg);};
	}
	var container = null;
	if (url)
	{
		container = document.createElement("a");
		container.href = url;
		container.appendChild(textNode);
		container.style.textDecoration = "none";
	}
	else
	{
		container = textNode;
	}
	
	container.style.position = "absolute";
	container.style.left = posX + "px";
	container.style.top = posY + "px";
	
	canvas.appendChild(container);
	
	return container;
};



Graphics.drawTitle = function(canvas, posX, posY, text, size, color, content, index)
{
	var mytarget = 'my-span' + index;
	
	var textNode = document.createElement("span");
	textNode.appendChild(document.createTextNode(text));
	textNode.id = mytarget;
	
	textNode.onmouseover = function()
	{
		if (content != null && content != "")
		{
			Ext.QuickTips.init();
			Ext.QuickTips.register({
				target: mytarget,
				text: content,
				width: 115,
				dismissDelay: 0
			});
		}
	};
	textNode.style.cursor = "default";
	textNode.style.color = color;
	textNode.style.fontSize = size + "px";
	textNode.style.fontFamily = "Arial, Helvetica, sans-serif";
	
	var container = textNode;
	container.style.position = "absolute";
	container.style.left = posX + "px";
	container.style.top = posY + "px";
	
	canvas.appendChild(container);
	
	return container;
};
