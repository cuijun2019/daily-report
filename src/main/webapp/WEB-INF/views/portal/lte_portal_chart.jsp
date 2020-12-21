<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<head>
</head>
<script type="text/javascript">

//雷达图
var polarConfig={
    credits: {
        enabled: false     //去掉highcharts网站url
    },
    chart: {
        renderTo: 'polarChart',
        polar: true,
        type: 'line'
    },
    title: {
        text: '载入中...'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [],
        labels: {
            //rotation: -45,
            align: 'center',
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif'
            }
        }
    },
    yAxis: {
        min:0,
        title: {
            text: '比例'
        },
        labels: {
            formatter: function() {
                return this.value;
            },
            style: {
                color: '#4572A7'
            }
        }
    },
    legend: {
        enabled: false
    },
    series: [{
        dataLabels: {
            enabled: true,
            rotation: -90,
            color: '',
            align: 'right',
            x: 4,
            y: 10,
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif',
                textShadow: '0 0 3px black'
            }
        },
        name: '值',
        data: []
    }]
};

//柱状
var columnConfig={
    credits: {
        enabled: false     //去掉highcharts网站url
    },
    chart: {
        renderTo: 'baseColumn',
        type: 'column'
    },
    title: {
        text: '载入中...'
    },
    subtitle: {
        text: ''
    },
    xAxis: {
        categories: [
        	'弱覆盖度',
        	'模三干扰强度',
        	'过覆盖度',
        	'重叠覆盖度'
        ],
        labels: {
            //rotation: -45,
            align: 'center',
            style: {
                fontSize: '13px',
                fontFamily: 'Verdana, sans-serif'
            }
        }
    },
    yAxis: {
        min:0,
        title: {
            //text: '比例'
        },
        labels: {
            formatter: function() {
                return this.value;
            },
            style: {
                color: '#4572A7'
            }
        }
    },
    legend: {
        enabled: false
    },
    series: []
};

$(document).ready(function() {
	findColumn();
});

function findColumn(){
	var series={};
	alert();
}

</script>
<body >
<div class="easyui-layout" data-options="fit:true"  >
	<div class="easyui-layout" data-options="fit:true"  >
		<div title="网络结构指标多维分布" data-options="region:'north',split:true,border:false" style=" height:250px; width: 100%;">
			<div id="polarChart" style="height:100%;width:100%;"></div>
		</div>
		<div title="" data-options="region:'center',split:true,border:false" style="height: 100%;width:100%;">
			<div id="baseColumn" style="height:100%;width:100%;"></div>
		</div>
	</div>
</div>
</body>