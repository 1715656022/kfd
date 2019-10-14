layui.use([ 'jquery' ], function() {
	var $ = layui.jquery;

	getpeopleCount(document.getElementById('peopleCount'), {
		text : [ '2019/10/12', '2019/10/11' ],
		time : [ '00:00 - 00:59', '02:00 - 02:59', '04:00 - 04:59' ],
		today : [ 820, 100, 901 ],
		yesterday : [ 700, 800, 400 ]
	});

	function getpeopleCount(id, data) {
		var peopleCountChart = echarts.init(id);
		var peopleCountOption = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : data.text,
				x : 'right'
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : data.time
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			yAxis : {
				type : 'value',
				name : '人',
			},
			series : [ {
				type : 'line',
				name : data.text[0],
				data : data.today,
				areaStyle : {},
				color : '#417DF5'
			}, {
				type : 'line',
				name : data.text[1],
				data : data.yesterday,
				smooth : false,
				itemStyle : {
					normal : {
						lineStyle : {
							width : 3,
							type : 'dotted'
						}
					}
				},
				color : '#9CB7FF'
			} ]
		};
		peopleCountChart.setOption(peopleCountOption);

		window.onresize = function() {
			peopleCountChart.resize();
		}
	}
});