$(document).ready(function() {
	var priceChart = $("#priceChart");
	$.ajax({
		url : priceChart.data("location") + "ajax?m=afs",
		method : "GET",
		success : function(data) {
			var date = [];
			var unitPrice = [];

			for ( var i in data) {
				date.push(data[i].date);
				unitPrice.push(data[i].unitPrice);
			}

			var chartdata = {
				labels : date,
				datasets : [ {
					backgroundColor : 'rgba(0, 0, 0, 0)',
					borderColor : 'rgba(255, 101, 80, 1)',
					data : unitPrice
				} ]
			};

			var barGraph = new Chart(priceChart, {
				type : 'line',
				data : chartdata,
				options : {
					legend : {
						display : false
					}
				},
			});
		},
		error : function(data) {
		}
	});

	var amountChart = $("#amountChart");
	$.ajax({
		url : amountChart.data("location") + "./ajax?m=aap",
		method : "GET",
		success : function(data) {
			var date = [];
			var paid = [];

			for ( var i in data) {
				date.push(data[i].date);
				paid.push(data[i].paid);
			}

			var chartdata = {
				labels : date,
				datasets : [ {
					backgroundColor : 'rgba(0, 0, 0, 0)',
					borderColor : 'rgba(152, 83, 204, 1)',
					data : paid
				} ]
			};

			
			var barGraph = new Chart(amountChart, {
				type : 'line',
				data : chartdata,
				options : {
					legend : {
						display : false
					}
				},
			});
		},
		error : function(data) {
		}
	});
});