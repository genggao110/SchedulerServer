/**
 * Copyright (c) 2006-2012, JGraph Ltd
 */
/**
 * Construcs a new toolbar for the given editor.
 */
function Toolbar(editorUi, container)
{
	this.editorUi = editorUi;
	this.container = container;
	this.staticElements = [];
	this.init();

	// Global handler to hide the current menu
	this.gestureHandler = mxUtils.bind(this, function(evt)
	{
		if (this.editorUi.currentMenu != null && mxEvent.getSource(evt) != this.editorUi.currentMenu.div)
		{
			this.hideMenu();
		}
	});

	mxEvent.addGestureListeners(document, this.gestureHandler);
};

/**
 * Image for the dropdown arrow.
 */
Toolbar.prototype.dropdownImage = (!mxClient.IS_SVG) ? IMAGE_PATH + '/dropdown.gif' : 'data:image/gif;base64,R0lGODlhDQANAIABAHt7e////yH/C1hNUCBEYXRhWE1QPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS4wLWMwNjAgNjEuMTM0Nzc3LCAyMDEwLzAyLzEyLTE3OjMyOjAwICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M1IE1hY2ludG9zaCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDpCREM1NkJFMjE0NEMxMUU1ODk1Q0M5MjQ0MTA4QjNDMSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpCREM1NkJFMzE0NEMxMUU1ODk1Q0M5MjQ0MTA4QjNDMSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkQzOUMzMjZCMTQ0QjExRTU4OTVDQzkyNDQxMDhCM0MxIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkQzOUMzMjZDMTQ0QjExRTU4OTVDQzkyNDQxMDhCM0MxIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+Af/+/fz7+vn49/b19PPy8fDv7u3s6+rp6Ofm5eTj4uHg397d3Nva2djX1tXU09LR0M/OzczLysnIx8bFxMPCwcC/vr28u7q5uLe2tbSzsrGwr66trKuqqainpqWko6KhoJ+enZybmpmYl5aVlJOSkZCPjo2Mi4qJiIeGhYSDgoGAf359fHt6eXh3dnV0c3JxcG9ubWxramloZ2ZlZGNiYWBfXl1cW1pZWFdWVVRTUlFQT05NTEtKSUhHRkVEQ0JBQD8+PTw7Ojk4NzY1NDMyMTAvLi0sKyopKCcmJSQjIiEgHx4dHBsaGRgXFhUUExIREA8ODQwLCgkIBwYFBAMCAQAAIfkEAQAAAQAsAAAAAA0ADQAAAhGMj6nL3QAjVHIu6azbvPtWAAA7';

/**
 * Image element for the dropdown arrow.
 */
Toolbar.prototype.dropdownImageHtml = '<img border="0" style="position:absolute;right:4px;top:' +
	((!EditorUi.compactUi) ? 8 : 6) + 'px;" src="' + Toolbar.prototype.dropdownImage + '" valign="middle"/>';

/**
 * Defines the background for selected buttons.
 */
Toolbar.prototype.selectedBackground = '#d0d0d0';

/**
 * Defines the background for selected buttons.
 */
Toolbar.prototype.unselectedBackground = 'none';

/**
 * Array that contains the DOM nodes that should never be removed.
 */
Toolbar.prototype.staticElements = null;

/**
 * Adds the toolbar elements.
 */
Toolbar.prototype.init = function()
{
	var sw = screen.width;
	
	// Takes into account initial compact mode
	sw -= (screen.height > 740) ? 56 : 0;
	
	if (sw >= 700)
	{
		var formatMenu = this.addMenu('', mxResources.get('view') + ' (' + mxResources.get('panTooltip') + ')', true, 'viewPanels', null, true);
		this.addDropDownArrow(formatMenu, 'geSprite-formatpanel', 38, 50, -4, -3, 36, -8);
		this.addSeparator();
	}
	//ReadTask
	if (sw >= 320)
	{
		var elts = this.addItems(['OpenLogicalTask']);
		elts[0].setAttribute('title', 'Load Logical Model');
	}

	this.addSeparator();
	if (sw >= 320)
	{
		var elts = this.addItems(['OpenComputationalTask']);
		elts[0].setAttribute('title', 'Load Computational Task');
	}
	//SaveTask
    if (sw >= 320)
    {
        var elts = this.addItems(['SaveTask']);
        elts[0].setAttribute('title', 'Save Computational Task');
	}
	//UpdateTask
    if (sw >= 320)
    {
        var elts = this.addItems(['UpdateTask']);
        elts[0].setAttribute('title', 'Update Computational Task');
    }
	this.addSeparator();
	var viewMenu = this.addMenu('', mxResources.get('zoom') + ' (Alt+Mousewheel)', true, 'viewZoom', null, true);
	viewMenu.showDisabled = true;
	viewMenu.style.whiteSpace = 'nowrap';
	viewMenu.style.position = 'relative';
	viewMenu.style.overflow = 'hidden';
	
	if (EditorUi.compactUi)
	{
		viewMenu.style.width = (mxClient.IS_QUIRKS) ? '58px' : '50px';
	}
	else
	{
		viewMenu.style.width = (mxClient.IS_QUIRKS) ? '62px' : '36px';
	}
	
	if (sw >= 420)
	{
		this.addSeparator();
		var elts = this.addItems(['zoomIn', 'zoomOut']);
		elts[0].setAttribute('title', mxResources.get('zoomIn') + ' (' + this.editorUi.actions.get('zoomIn').shortcut + ')');
		elts[1].setAttribute('title', mxResources.get('zoomOut') + ' (' + this.editorUi.actions.get('zoomOut').shortcut + ')');
	}
	
	// Updates the label if the scale changes
	this.updateZoom = mxUtils.bind(this, function()
	{
		viewMenu.innerHTML = Math.round(this.editorUi.editor.graph.view.scale * 100) + '%' +
			this.dropdownImageHtml;
		
		if (EditorUi.compactUi)
		{
			viewMenu.getElementsByTagName('img')[0].style.right = '1px';
			viewMenu.getElementsByTagName('img')[0].style.top = '5px';
		}
	});

	this.editorUi.editor.graph.view.addListener(mxEvent.EVENT_SCALE, this.updateZoom);
	this.editorUi.editor.addListener('resetGraphView', this.updateZoom);

	var elts = this.addItems(['-', 'undo', 'redo']);
	elts[1].setAttribute('title', mxResources.get('undo') + ' (' + this.editorUi.actions.get('undo').shortcut + ')');
	elts[2].setAttribute('title', mxResources.get('redo') + ' (' + this.editorUi.actions.get('redo').shortcut + ')');
	
	if (sw >= 320)
	{
		var elts = this.addItems(['-', 'delete']);
		elts[1].setAttribute('title', mxResources.get('delete') + ' (' + this.editorUi.actions.get('delete').shortcut + ')');
	}
	
	if (sw >= 550)
	{
		this.addItems(['-', 'toFront', 'toBack']);
	}

	if (sw >= 740)
	{
		this.addItems(['-', 'fillColor']);
		
		if (sw >= 780)
		{
			this.addItems(['strokeColor']);
			
			if (sw >= 820)
			{
				this.addItems(['shadow']);
			}
		}
	}
	
	if (sw >= 400)
	{
		this.addSeparator();
		
		if (sw >= 440)
		{
			this.edgeShapeMenu = this.addMenuFunction('', mxResources.get('connection'), false, mxUtils.bind(this, function(menu)
			{
				this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_SHAPE, 'width'], [null, null], 'geIcon geSprite geSprite-connection', null, true).setAttribute('title', mxResources.get('line'));
				this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_SHAPE, 'width'], ['link', null], 'geIcon geSprite geSprite-linkedge', null, true).setAttribute('title', mxResources.get('link'));
				this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_SHAPE, 'width'], ['flexArrow', null], 'geIcon geSprite geSprite-arrow', null, true).setAttribute('title', mxResources.get('arrow'));
				this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_SHAPE, 'width'], ['arrow', null], 'geIcon geSprite geSprite-simplearrow', null, true).setAttribute('title', mxResources.get('simpleArrow'));
			}));
	
			this.addDropDownArrow(this.edgeShapeMenu, 'geSprite-connection', 44, 50, 0, 0, 22, -4);
		}
	
		this.edgeStyleMenu = this.addMenuFunction('geSprite-orthogonal', mxResources.get('waypoints'), false, mxUtils.bind(this, function(menu)
		{
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], [null, null, null], 'geIcon geSprite geSprite-straight', null, true).setAttribute('title', mxResources.get('straight'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['orthogonalEdgeStyle', null, null], 'geIcon geSprite geSprite-orthogonal', null, true).setAttribute('title', mxResources.get('orthogonal'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_ELBOW, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['elbowEdgeStyle', null, null, null], 'geIcon geSprite geSprite-horizontalelbow', null, true).setAttribute('title', mxResources.get('simple'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_ELBOW, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['elbowEdgeStyle', 'vertical', null, null], 'geIcon geSprite geSprite-verticalelbow', null, true).setAttribute('title', mxResources.get('simple'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_ELBOW, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['isometricEdgeStyle', null, null, null], 'geIcon geSprite geSprite-horizontalisometric', null, true).setAttribute('title', mxResources.get('isometric'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_ELBOW, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['isometricEdgeStyle', 'vertical', null, null], 'geIcon geSprite geSprite-verticalisometric', null, true).setAttribute('title', mxResources.get('isometric'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['orthogonalEdgeStyle', '1', null], 'geIcon geSprite geSprite-curved', null, true).setAttribute('title', mxResources.get('curved'));
			this.editorUi.menus.edgeStyleChange(menu, '', [mxConstants.STYLE_EDGE, mxConstants.STYLE_CURVED, mxConstants.STYLE_NOEDGESTYLE], ['entityRelationEdgeStyle', null, null], 'geIcon geSprite geSprite-entity', null, true).setAttribute('title', mxResources.get('entityRelation'));
		}));
		
		this.addDropDownArrow(this.edgeStyleMenu, 'geSprite-orthogonal', 44, 50, 0, 0, 22, -4);
	}

	this.addSeparator();

	var insertMenu = this.addMenu('', mxResources.get('insert') + ' (' + mxResources.get('doubleClickTooltip') + ')', true, 'insert', null, true);
	this.addDropDownArrow(insertMenu, 'geSprite-plus', 38, 48, -4, -3, 36, -8);
	this.addSeparator();

    var openBtn = document.createElement("a");
    openBtn.className = "geButton";
    openBtn.setAttribute("title","Open MxGraphList");
    openBtn.style.textAlign = "center";
    var openIcon = document.createElement("i");
    openIcon.className = "fa fa-folder-open-o";
    openIcon.style.width ="21px";
    openIcon.style.height ="21px";
    openIcon.style.lineHeight = "24px";
    openIcon.style.fontSize = "16px";
    openBtn.appendChild(openIcon);
    var editorUi = this.editorUi;
    openBtn.onclick = function () {
        editorUi.showDialog(new OpenNewFile(editorUi).container, 470, 130, true, true, function()
        {
            window.openFile = null;
        });
    };
    this.container.append(openBtn);

	var openBtn2 = document.createElement("a");
	openBtn2.className = "geButton";
	openBtn2.setAttribute("title","Open LogicalScene");
	openBtn2.style.textAlign = "center";
	var openIcon2 = document.createElement("i");
	openIcon2.className = "fa fa-folder-open-o";
	openIcon2.style.width ="21px";
	openIcon2.style.height ="21px";
	openIcon2.style.lineHeight = "24px";
	openIcon2.style.fontSize = "16px";
	openBtn2.appendChild(openIcon2);
	var editorUi = this.editorUi;
	openBtn2.onclick = function () {
		editorUi.showDialog(new OpenNewFile2(editorUi).container, 470, 130, true, true, function()
		{
			window.openFile = null;
		});
	};
	this.container.append(openBtn2);
	//------------------------------------------------
	var openBtn3 = document.createElement("a");
    openBtn3.className = "geButton";
    openBtn3.setAttribute("title","Open ComputationalScene");
    openBtn3.style.textAlign = "center";
    var openIcon3 = document.createElement("i");
    openIcon3.className = "fa fa-folder-open-o";
    openIcon3.style.width ="21px";
    openIcon3.style.height ="21px";
    openIcon3.style.lineHeight = "24px";
    openIcon3.style.fontSize = "16px";
    openBtn3.appendChild(openIcon3);
    var editorUi = this.editorUi;
    openBtn3.onclick = function () {
		editorUi.showDialog(new OpenNewFile3(editorUi).container, 470, 130, true, true, function()
		{
			window.openFile = null;
		});
    };
	this.container.append(openBtn3);
	///----------------------------------------------------
    this.addSeparator();

    var select = document.createElement("select");
    select.className = "geLabel";
    select.id = "viewPanel";
    select.style.width = "135px";
    var option = document.createElement("option");
    option.innerHTML = "Integration View";
    option.value = "Integration";
	option.setAttribute("currentUID","basicXML");
	select.appendChild(option);
    var graph = this.editorUi.editor.graph;

    select.onchange = function () {
		var graphUID=$("#viewPanel option:selected").attr("currentUID");
		for(var i=0;i<graph.mxgraphList.length;i++){
			if(graph.mxgraphList[i].uid==graphUID){
				var doc = mxUtils.parseXml(graph.mxgraphList[i].graphXML);
				var dec = new mxCodec(doc);
				dec.decode(doc.documentElement, graph.getModel());
				break;
			}
		}
		for(var i=select.childElementCount-1;i>$("#viewPanel").get(0).selectedIndex;i--){
			select.options.remove(i);
		}
    };

	var button = document.createElement("a");
	button.className = "geButton";
	button.style.textAlign = "center";
	button.setAttribute("title","Download ComputationalScene");
	var saveIcon = document.createElement("i");
	saveIcon.className = "fa fa-download";
	saveIcon.style.width ="21px";
	saveIcon.style.height ="21px";
	saveIcon.style.lineHeight = "24px";
	saveIcon.style.fontSize = "16px";
	button.appendChild(saveIcon);
	button.onclick = function () {
		// var ComputationalSceneXMLStr=graph.getComputationalSceneXMLStr();
		// var blob = new Blob([ComputationalSceneXMLStr], {type: "text/plain;charset=utf-8"}),
		// fileName = 'computationalScene.mdl';
		var ComputationalServiceXMLStr=graph.getComputationalServiceXMLStr();
		var blob = new Blob([ComputationalServiceXMLStr], {type: "text/plain;charset=utf-8"});
		fileName = 'computationalService.xml';
		if (window.navigator.msSaveOrOpenBlob) {
			navigator.msSaveBlob(blob, fileName);
		} else {
			var link = document.createElement('a');
			link.href = window.URL.createObjectURL(blob);
			link.download = fileName;
			link.click();
			window.URL.revokeObjectURL(link.href);
		}
	};
	this.container.append(button);

    this.container.append(select);
	//------------------------------------------------
	var runModel = document.createElement("a");
    runModel.className = "geButton";
    runModel.setAttribute("title","Run models");
    runModel.style.textAlign = "center";
    var runModelIcon = document.createElement("i");
	runModelIcon.className = "material-icons";
	runModelIcon.innerHTML="play_circle_filled_white";
    runModelIcon.style.width ="21px";
    runModelIcon.style.height ="21px";
    runModelIcon.style.lineHeight = "24px";
    runModelIcon.style.fontSize = "16px";
    runModel.appendChild(runModelIcon);
    var editorUi = this.editorUi;
    runModel.onclick = function () {
		// window.globalModalVM.showConfirm();
		// window.runModalVM.showRunModal();

		//王明版本
		console.log(window.modelTaskConfig);
		var graph = editorUi.editor.graph;
		var ComputationalServiceXMLStr = graph.getComputationalServiceXMLStr();
		var comServiceXMLBlob = new Blob([ComputationalServiceXMLStr], {type: "text/plain;charset=utf-8"});
		var formdata = new FormData();
		formdata.append('file',comServiceXMLBlob, 'ComputationalService.xml');
		//此处上传运行配置到服务器
		var modelItems = window.modelTaskConfig.modelItems;
		var optionalServiceConfig = [];
		for(let i=0;i<modelItems.length;i++){
			var optionalModelservice={}
			optionalModelservice.pid=modelItems[i].pid;
			optionalModelservice.optional=modelItems[i].serviceOptions;
			optionalServiceConfig.push(optionalModelservice);
		}
		console.log(optionalServiceConfig);
		//此处上传备用配置到服务器

		// var reg = /groupID=(\S*)/;
		// var url = window.location.href;
		// if (url.search(reg) != -1) {
		// 	let groupID = url.match(reg)[1];
		// 	var graph = editorUi.editor.graph;
		// 	var ComputationalXMLStr = graph.getComputationalSceneXMLStr();
		// 	window.runModelUid = guid();
		// 	var uploadData = {
		// 		"roomId" : groupID,
		// 		"xmlStr" : ComputationalXMLStr,
		// 		"objectId" :window.runModelUid
		// 	}
		// 	$.ajax({
		// 		url: "http://172.21.213.33:8080/computableModel/data",
		// 		type: "POST",
		// 		async: false,
		// 		data: uploadData,
		// 		dataType:'json',
		// 		crossDomain: true,
		// 		success: function (data) {
		// 			console.log("Upload config success.");
		// 		},
		// 		error: function (err) {
		// 			console.log("Upload config fail.");
		// 		}
		// 	});
		// 	editorUi.showDialog(new RunModels(editorUi).container, 500, 300, true, false);
		// 	function guid() {
		// 		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		// 			var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		// 			return v.toString(16);
		// 		});
		// 	}
		// }else{
		// 	confirm('url is error.');
		// }
    };
	this.container.append(runModel);
};

/**
 * Adds the toolbar elements.
 */
Toolbar.prototype.addDropDownArrow = function(menu, sprite, width, atlasWidth, left, top, atlasDelta, atlasLeft)
{
	atlasDelta = (atlasDelta != null) ? atlasDelta : 32;
	left = (EditorUi.compactUi) ? left : atlasLeft;
	
	menu.style.whiteSpace = 'nowrap';
	menu.style.overflow = 'hidden';
	menu.style.position = 'relative';
	menu.innerHTML = '<div class="geSprite ' + sprite + '" style="margin-left:' + left + 'px;margin-top:' + top + 'px;"></div>' +
		this.dropdownImageHtml;
	menu.style.width = (mxClient.IS_QUIRKS) ? atlasWidth + 'px' : (atlasWidth - atlasDelta) + 'px';
	
	if (mxClient.IS_QUIRKS)
	{
		menu.style.height = (EditorUi.compactUi) ? '24px' : '26px';
	}
	
	// Fix for item size in kennedy theme
	if (EditorUi.compactUi)
	{
		menu.getElementsByTagName('img')[0].style.left = '24px';
		menu.getElementsByTagName('img')[0].style.top = '5px';
		menu.style.width = (mxClient.IS_QUIRKS) ? width + 'px' : (width - 10) + 'px';
	}
};

/**
 * Sets the current font name.
 */
Toolbar.prototype.setFontName = function(value)
{
	if (this.fontMenu != null)
	{
		this.fontMenu.innerHTML = '<div style="width:60px;overflow:hidden;display:inline-block;">' +
			mxUtils.htmlEntities(value) + '</div>' + this.dropdownImageHtml;
	}
};

/**
 * Sets the current font name.
 */
Toolbar.prototype.setFontSize = function(value)
{
	if (this.sizeMenu != null)
	{
		this.sizeMenu.innerHTML = '<div style="width:24px;overflow:hidden;display:inline-block;">' +
			value + '</div>' + this.dropdownImageHtml;
	}
};

/**
 * Hides the current menu.
 */
Toolbar.prototype.createTextToolbar = function()
{
	var graph = this.editorUi.editor.graph;

	var styleElt = this.addMenu('', mxResources.get('style'), true, 'formatBlock');
	styleElt.style.position = 'relative';
	styleElt.style.whiteSpace = 'nowrap';
	styleElt.style.overflow = 'hidden';
	styleElt.innerHTML = mxResources.get('style') + this.dropdownImageHtml;
	
	if (EditorUi.compactUi)
	{
		styleElt.style.paddingRight = '18px';
		styleElt.getElementsByTagName('img')[0].style.right = '1px';
		styleElt.getElementsByTagName('img')[0].style.top = '5px';
	}
	
	this.addSeparator();
	
	this.fontMenu = this.addMenu('', mxResources.get('fontFamily'), true, 'fontFamily');
	this.fontMenu.style.position = 'relative';
	this.fontMenu.style.whiteSpace = 'nowrap';
	this.fontMenu.style.overflow = 'hidden';
	this.fontMenu.style.width = (mxClient.IS_QUIRKS) ? '80px' : '60px';
	
	this.setFontName(Menus.prototype.defaultFont);
	
	if (EditorUi.compactUi)
	{
		this.fontMenu.style.paddingRight = '18px';
		this.fontMenu.getElementsByTagName('img')[0].style.right = '1px';
		this.fontMenu.getElementsByTagName('img')[0].style.top = '5px';
	}
	
	this.addSeparator();
	
	this.sizeMenu = this.addMenu(Menus.prototype.defaultFontSize, mxResources.get('fontSize'), true, 'fontSize');
	this.sizeMenu.style.position = 'relative';
	this.sizeMenu.style.whiteSpace = 'nowrap';
	this.sizeMenu.style.overflow = 'hidden';
	this.sizeMenu.style.width = (mxClient.IS_QUIRKS) ? '44px' : '24px';
	
	this.setFontSize(Menus.prototype.defaultFontSize);
	
	if (EditorUi.compactUi)
	{
		this.sizeMenu.style.paddingRight = '18px';
		this.sizeMenu.getElementsByTagName('img')[0].style.right = '1px';
		this.sizeMenu.getElementsByTagName('img')[0].style.top = '5px';
	}
	
	var elts = this.addItems(['-', 'undo', 'redo','-', 'bold', 'italic', 'underline']);
	elts[1].setAttribute('title', mxResources.get('undo') + ' (' + this.editorUi.actions.get('undo').shortcut + ')');
	elts[2].setAttribute('title', mxResources.get('redo') + ' (' + this.editorUi.actions.get('redo').shortcut + ')');
	elts[4].setAttribute('title', mxResources.get('bold') + ' (' + this.editorUi.actions.get('bold').shortcut + ')');
	elts[5].setAttribute('title', mxResources.get('italic') + ' (' + this.editorUi.actions.get('italic').shortcut + ')');
	elts[6].setAttribute('title', mxResources.get('underline') + ' (' + this.editorUi.actions.get('underline').shortcut + ')');

	// KNOWN: Lost focus after click on submenu with text (not icon) in quirks and IE8. This is because the TD seems
	// to catch the focus on click in these browsers. NOTE: Workaround in mxPopupMenu for icon items (without text).
	var alignMenu = this.addMenuFunction('', mxResources.get('align'), false, mxUtils.bind(this, function(menu)
	{
		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('justifyleft', false, null);
		}), null, 'geIcon geSprite geSprite-left');
		elt.setAttribute('title', mxResources.get('left'));

		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('justifycenter', false, null);
		}), null, 'geIcon geSprite geSprite-center');
		elt.setAttribute('title', mxResources.get('center'));

		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('justifyright', false, null);
		}), null, 'geIcon geSprite geSprite-right');
		elt.setAttribute('title', mxResources.get('right'));

		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('justifyfull', false, null);
		}), null, 'geIcon geSprite geSprite-justifyfull');
		elt.setAttribute('title', mxResources.get('justifyfull'));
		
		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('insertorderedlist', false, null);
		}), null, 'geIcon geSprite geSprite-orderedlist');
		elt.setAttribute('title', mxResources.get('numberedList'));
		
		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('insertunorderedlist', false, null);
		}), null, 'geIcon geSprite geSprite-unorderedlist');
		elt.setAttribute('title', mxResources.get('bulletedList'));
		
		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('outdent', false, null);
		}), null, 'geIcon geSprite geSprite-outdent');
		elt.setAttribute('title', mxResources.get('decreaseIndent'));
		
		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('indent', false, null);
		}), null, 'geIcon geSprite geSprite-indent');
		elt.setAttribute('title', mxResources.get('increaseIndent'));
	}));

	alignMenu.style.position = 'relative';
	alignMenu.style.whiteSpace = 'nowrap';
	alignMenu.style.overflow = 'hidden';
	alignMenu.innerHTML = '<div class="geSprite geSprite-left" style="margin-left:-2px;"></div>' + this.dropdownImageHtml;
	alignMenu.style.width = (mxClient.IS_QUIRKS) ? '50px' : '30px';

	if (EditorUi.compactUi)
	{
		alignMenu.getElementsByTagName('img')[0].style.left = '22px';
		alignMenu.getElementsByTagName('img')[0].style.top = '5px';
	}
	
	var formatMenu = this.addMenuFunction('', mxResources.get('format'), false, mxUtils.bind(this, function(menu)
	{
		elt = menu.addItem('', null, this.editorUi.actions.get('subscript').funct,
			null, 'geIcon geSprite geSprite-subscript');
		elt.setAttribute('title', mxResources.get('subscript') + ' (' + Editor.ctrlKey + '+,)');

		elt = menu.addItem('', null, this.editorUi.actions.get('superscript').funct,
			null, 'geIcon geSprite geSprite-superscript');
		elt.setAttribute('title', mxResources.get('superscript') + ' (' + Editor.ctrlKey + '+.)');

		// KNOWN: IE+FF don't return keyboard focus after color dialog (calling focus doesn't help)
		elt = menu.addItem('', null, this.editorUi.actions.get('fontColor').funct,
			null, 'geIcon geSprite geSprite-fontcolor');
		elt.setAttribute('title', mxResources.get('fontColor'));
		
		elt = menu.addItem('', null, this.editorUi.actions.get('backgroundColor').funct,
			null, 'geIcon geSprite geSprite-fontbackground');
		elt.setAttribute('title', mxResources.get('backgroundColor'));
		
		elt = menu.addItem('', null, mxUtils.bind(this, function()
		{
			document.execCommand('removeformat', false, null);
		}), null, 'geIcon geSprite geSprite-removeformat');
		elt.setAttribute('title', mxResources.get('removeFormat'));
	}));

	formatMenu.style.position = 'relative';
	formatMenu.style.whiteSpace = 'nowrap';
	formatMenu.style.overflow = 'hidden';
	formatMenu.innerHTML = '<div class="geSprite geSprite-dots" style="margin-left:-2px;"></div>' +
		this.dropdownImageHtml;
	formatMenu.style.width = (mxClient.IS_QUIRKS) ? '50px' : '30px';

	if (EditorUi.compactUi)
	{
		formatMenu.getElementsByTagName('img')[0].style.left = '22px';
		formatMenu.getElementsByTagName('img')[0].style.top = '5px';
	}

	this.addSeparator();

	this.addButton('geIcon geSprite geSprite-code', mxResources.get('html'), function()
	{
		graph.cellEditor.toggleViewMode();
		
		if (graph.cellEditor.textarea.innerHTML.length > 0 && (graph.cellEditor.textarea.innerHTML != '&nbsp;' || !graph.cellEditor.clearOnChange))
		{
			window.setTimeout(function()
			{
				document.execCommand('selectAll', false, null);
			});
		}
	});
	
	this.addSeparator();
	
	// FIXME: Uses geButton here and geLabel in main menu
	var insertMenu = this.addMenuFunction('', mxResources.get('insert'), true, mxUtils.bind(this, function(menu)
	{
		menu.addItem(mxResources.get('insertLink'), null, mxUtils.bind(this, function()
		{
			this.editorUi.actions.get('link').funct();
		}));
		
		menu.addItem(mxResources.get('insertImage'), null, mxUtils.bind(this, function()
		{
			this.editorUi.actions.get('image').funct();
		}));
		
		menu.addItem(mxResources.get('insertHorizontalRule'), null, mxUtils.bind(this, function()
		{
			document.execCommand('inserthorizontalrule', false, null);
		}));
	}));
	
	insertMenu.style.whiteSpace = 'nowrap';
	insertMenu.style.overflow = 'hidden';
	insertMenu.style.position = 'relative';
	insertMenu.innerHTML = '<div class="geSprite geSprite-plus" style="margin-left:-4px;margin-top:-3px;"></div>' +
		this.dropdownImageHtml;
	insertMenu.style.width = (mxClient.IS_QUIRKS) ? '36px' : '16px';
	
	// Fix for item size in kennedy theme
	if (EditorUi.compactUi)
	{
		insertMenu.getElementsByTagName('img')[0].style.left = '24px';
		insertMenu.getElementsByTagName('img')[0].style.top = '5px';
		insertMenu.style.width = (mxClient.IS_QUIRKS) ? '50px' : '30px';
	}
	
	this.addSeparator();
	
	// KNOWN: All table stuff does not work with undo/redo
	// KNOWN: Lost focus after click on submenu with text (not icon) in quirks and IE8. This is because the TD seems
	// to catch the focus on click in these browsers. NOTE: Workaround in mxPopupMenu for icon items (without text).
	var elt = this.addMenuFunction('geIcon geSprite geSprite-table', mxResources.get('table'), false, mxUtils.bind(this, function(menu)
	{
		var elt = graph.getSelectedElement();
		var cell = graph.getParentByName(elt, 'TD', graph.cellEditor.text2);
		var row = graph.getParentByName(elt, 'TR', graph.cellEditor.text2);

		if (row == null)
    	{
			this.editorUi.menus.addInsertTableItem(menu);
    	}
		else
    	{
			var table = graph.getParentByName(row, 'TABLE', graph.cellEditor.text2);

			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				try
				{
					graph.selectNode(graph.insertColumn(table, (cell != null) ? cell.cellIndex : 0));
				}
				catch (e)
				{
					mxUtils.alert(mxResources.get('error') + ': ' + e.message);
				}
			}), null, 'geIcon geSprite geSprite-insertcolumnbefore');
			elt.setAttribute('title', mxResources.get('insertColumnBefore'));
			
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{	
				try
				{
					graph.selectNode(graph.insertColumn(table, (cell != null) ? cell.cellIndex + 1 : -1));
				}
				catch (e)
				{
					mxUtils.alert(mxResources.get('error') + ': ' + e.message);
				}
			}), null, 'geIcon geSprite geSprite-insertcolumnafter');
			elt.setAttribute('title', mxResources.get('insertColumnAfter'));

			elt = menu.addItem('Delete column', null, mxUtils.bind(this, function()
			{
				if (cell != null)
				{
					try
					{
						graph.deleteColumn(table, cell.cellIndex);
					}
					catch (e)
					{
						mxUtils.alert(mxResources.get('error') + ': ' + e.message);
					}
				}
			}), null, 'geIcon geSprite geSprite-deletecolumn');
			elt.setAttribute('title', mxResources.get('deleteColumn'));
			
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				try
				{
					graph.selectNode(graph.insertRow(table, row.sectionRowIndex));
				}
				catch (e)
				{
					mxUtils.alert(mxResources.get('error') + ': ' + e.message);
				}
			}), null, 'geIcon geSprite geSprite-insertrowbefore');
			elt.setAttribute('title', mxResources.get('insertRowBefore'));

			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				try
				{
					graph.selectNode(graph.insertRow(table, row.sectionRowIndex + 1));
				}
				catch (e)
				{
					mxUtils.alert(mxResources.get('error') + ': ' + e.message);
				}
			}), null, 'geIcon geSprite geSprite-insertrowafter');
			elt.setAttribute('title', mxResources.get('insertRowAfter'));

			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				try
				{
					graph.deleteRow(table, row.sectionRowIndex);
				}
				catch (e)
				{
					mxUtils.alert(mxResources.get('error') + ': ' + e.message);
				}
			}), null, 'geIcon geSprite geSprite-deleterow');
			elt.setAttribute('title', mxResources.get('deleteRow'));
			
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				// Converts rgb(r,g,b) values
				var color = table.style.borderColor.replace(
					    /\brgb\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/g,
					    function($0, $1, $2, $3) {
					        return "#" + ("0"+Number($1).toString(16)).substr(-2) + ("0"+Number($2).toString(16)).substr(-2) + ("0"+Number($3).toString(16)).substr(-2);
					    });
				this.editorUi.pickColor(color, function(newColor)
				{
					if (newColor == null || newColor == mxConstants.NONE)
					{
						table.removeAttribute('border');
						table.style.border = '';
						table.style.borderCollapse = '';
					}
					else
					{
						table.setAttribute('border', '1');
						table.style.border = '1px solid ' + newColor;
						table.style.borderCollapse = 'collapse';
					}
				});
			}), null, 'geIcon geSprite geSprite-strokecolor');
			elt.setAttribute('title', mxResources.get('borderColor'));
			
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				// Converts rgb(r,g,b) values
				var color = table.style.backgroundColor.replace(
					    /\brgb\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/g,
					    function($0, $1, $2, $3) {
					        return "#" + ("0"+Number($1).toString(16)).substr(-2) + ("0"+Number($2).toString(16)).substr(-2) + ("0"+Number($3).toString(16)).substr(-2);
					    });
				this.editorUi.pickColor(color, function(newColor)
				{
					if (newColor == null || newColor == mxConstants.NONE)
					{
						table.style.backgroundColor = '';
					}
					else
					{
						table.style.backgroundColor = newColor;
					}
				});
			}), null, 'geIcon geSprite geSprite-fillcolor');
			elt.setAttribute('title', mxResources.get('backgroundColor'));
			
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				var value = table.getAttribute('cellPadding') || 0;
				
				var dlg = new FilenameDialog(this.editorUi, value, mxResources.get('apply'), mxUtils.bind(this, function(newValue)
				{
					if (newValue != null && newValue.length > 0)
					{
						table.setAttribute('cellPadding', newValue);
					}
					else
					{
						table.removeAttribute('cellPadding');
					}
				}), mxResources.get('spacing'));
				this.editorUi.showDialog(dlg.container, 300, 80, true, true);
				dlg.init();
			}), null, 'geIcon geSprite geSprite-fit');
			elt.setAttribute('title', mxResources.get('spacing'));
			
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				table.setAttribute('align', 'left');
			}), null, 'geIcon geSprite geSprite-left');
			elt.setAttribute('title', mxResources.get('left'));

			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				table.setAttribute('align', 'center');
			}), null, 'geIcon geSprite geSprite-center');
			elt.setAttribute('title', mxResources.get('center'));
				
			elt = menu.addItem('', null, mxUtils.bind(this, function()
			{
				table.setAttribute('align', 'right');
			}), null, 'geIcon geSprite geSprite-right');
			elt.setAttribute('title', mxResources.get('right'));
    	}
	}));
	
	elt.style.position = 'relative';
	elt.style.whiteSpace = 'nowrap';
	elt.style.overflow = 'hidden';
	elt.innerHTML = '<div class="geSprite geSprite-table" style="margin-left:-2px;"></div>' + this.dropdownImageHtml;
	elt.style.width = (mxClient.IS_QUIRKS) ? '50px' : '30px';

	// Fix for item size in kennedy theme
	if (EditorUi.compactUi)
	{
		elt.getElementsByTagName('img')[0].style.left = '22px';
		elt.getElementsByTagName('img')[0].style.top = '5px';
	}

};

/**
 * Hides the current menu.
 */
Toolbar.prototype.hideMenu = function()
{
	this.editorUi.hideCurrentMenu();
};

/**
 * Adds a label to the toolbar.
 */
Toolbar.prototype.addMenu = function(label, tooltip, showLabels, name, c, showAll)
{
	var menu = this.editorUi.menus.get(name);
	var elt = this.addMenuFunction(label, tooltip, showLabels, function()
	{
		menu.funct.apply(menu, arguments);
	}, c, showAll);
	
	menu.addListener('stateChanged', function()
	{
		elt.setEnabled(menu.enabled);
	});

	return elt;
};

/**
 * Adds a label to the toolbar.
 */
Toolbar.prototype.addMenuFunction = function(label, tooltip, showLabels, funct, c, showAll)
{
	return this.addMenuFunctionInContainer((c != null) ? c : this.container, label, tooltip, showLabels, funct, showAll);
};

/**
 * Adds a label to the toolbar.
 */
Toolbar.prototype.addMenuFunctionInContainer = function(container, label, tooltip, showLabels, funct, showAll)
{
	var elt = (showLabels) ? this.createLabel(label) : this.createButton(label);
	this.initElement(elt, tooltip);
	this.addMenuHandler(elt, showLabels, funct, showAll);
	container.appendChild(elt);
	
	return elt;
};

/**
 * Adds a separator to the separator.
 */
Toolbar.prototype.addSeparator = function(c)
{
	c = (c != null) ? c : this.container;
	var elt = document.createElement('div');
	elt.className = 'geSeparator';
	c.appendChild(elt);
	
	return elt;
};

/**
 * Adds given action item
 */
Toolbar.prototype.addItems = function(keys, c, ignoreDisabled)
{
	var items = [];
	
	for (var i = 0; i < keys.length; i++)
	{
		var key = keys[i];
		
		if (key == '-')
		{
			items.push(this.addSeparator(c));
		}
		else
		{
			items.push(this.addItem('geSprite-' + key.toLowerCase(), key, c, ignoreDisabled));
		}
	}
	
	return items;
};

/**
 * Adds given action item
 */
Toolbar.prototype.addItem = function(sprite, key, c, ignoreDisabled)
{
	var action = this.editorUi.actions.get(key);
	var elt = null;
	
	if (action != null)
	{
		var tooltip = action.label;
		
		if (action.shortcut != null)
		{
			tooltip += ' (' + action.shortcut + ')';
		}
		
		elt = this.addButton(sprite, tooltip, action.funct, c);

		if (!ignoreDisabled)
		{
			elt.setEnabled(action.enabled);
			
			action.addListener('stateChanged', function()
			{
				elt.setEnabled(action.enabled);
			});
		}
	}
	
	return elt;
};

/**
 * Adds a button to the toolbar.
 */
Toolbar.prototype.addButton = function(classname, tooltip, funct, c)
{
	var elt = this.createButton(classname);
	c = (c != null) ? c : this.container;
	
	this.initElement(elt, tooltip);
	this.addClickHandler(elt, funct);
	c.appendChild(elt);
	
	return elt;
};

/**
 * Initializes the given toolbar element.
 */
Toolbar.prototype.initElement = function(elt, tooltip)
{
	// Adds tooltip
	if (tooltip != null)
	{
		elt.setAttribute('title', tooltip);
	}

	this.addEnabledState(elt);
};

/**
 * Adds enabled state with setter to DOM node (avoids JS wrapper).
 */
Toolbar.prototype.addEnabledState = function(elt)
{
	var classname = elt.className;
	
	elt.setEnabled = function(value)
	{
		elt.enabled = value;
		
		if (value)
		{
			elt.className = classname;
		}
		else
		{
			elt.className = classname + ' mxDisabled';
		}
	};
	
	elt.setEnabled(true);
};

/**
 * Adds enabled state with setter to DOM node (avoids JS wrapper).
 */
Toolbar.prototype.addClickHandler = function(elt, funct)
{
	if (funct != null)
	{
		mxEvent.addListener(elt, 'click', function(evt)
		{
			if (elt.enabled)
			{
				funct(evt);
			}
			
			mxEvent.consume(evt);
		});
		
		if (document.documentMode != null && document.documentMode >= 9)
		{
			// Prevents focus
			mxEvent.addListener(elt, 'mousedown', function(evt)
			{
				evt.preventDefault();
			});
		}
	}
};

/**
 * Creates and returns a new button.
 */
Toolbar.prototype.createButton = function(classname)
{
	var elt = document.createElement('a');
	elt.setAttribute('href', 'javascript:void(0);');
	elt.className = 'geButton';

	var inner = document.createElement('div');
	
	if (classname != null)
	{
		inner.className = 'geSprite ' + classname;
	}
	
	elt.appendChild(inner);
	
	return elt;
};

/**
 * Creates and returns a new button.
 */
Toolbar.prototype.createLabel = function(label, tooltip)
{
	var elt = document.createElement('a');
	elt.setAttribute('href', 'javascript:void(0);');
	elt.className = 'geLabel';
	mxUtils.write(elt, label);
	
	return elt;
};

/**
 * Adds a handler for showing a menu in the given element.
 */
Toolbar.prototype.addMenuHandler = function(elt, showLabels, funct, showAll)
{
	if (funct != null)
	{
		var graph = this.editorUi.editor.graph;
		var menu = null;
		var show = true;

		mxEvent.addListener(elt, 'click', mxUtils.bind(this, function(evt)
		{
			if (show && (elt.enabled == null || elt.enabled))
			{
				graph.popupMenuHandler.hideMenu();
				menu = new mxPopupMenu(funct);
				menu.div.className += ' geToolbarMenu';
				menu.showDisabled = showAll;
				menu.labels = showLabels;
				menu.autoExpand = true;
				
				var offset = mxUtils.getOffset(elt);
				menu.popup(offset.x, offset.y + elt.offsetHeight, null, evt);
				this.editorUi.setCurrentMenu(menu, elt);
				
				// Workaround for scrollbar hiding menu items
				if (!showLabels && menu.div.scrollHeight > menu.div.clientHeight)
				{
					menu.div.style.width = '40px';
				}
				
				menu.hideMenu = mxUtils.bind(this, function()
				{
					mxPopupMenu.prototype.hideMenu.apply(menu, arguments);
					this.editorUi.resetCurrentMenu();
					menu.destroy();
				});
				
				// Extends destroy to reset global state
				menu.addListener(mxEvent.EVENT_HIDE, mxUtils.bind(this, function()
				{
					this.currentElt = null;
				}));
			}
			
			show = true;
			mxEvent.consume(evt);
		}));

		// Hides menu if already showing
		mxEvent.addListener(elt, 'mousedown', mxUtils.bind(this, function(evt)
		{
			show = this.currentElt != elt;
			
			// Prevents focus
			if (document.documentMode != null && document.documentMode >= 9)
			{
				evt.preventDefault();
			}
		}));
	}
};

/**
 * Adds a handler for showing a menu in the given element.
 */
Toolbar.prototype.destroy = function()
{
	if (this.gestureHandler != null)
	{	
		mxEvent.removeGestureListeners(document, this.gestureHandler);
		this.gestureHandler = null;
	}
};
