$(document).ready(
		function() {
			$(function() {
				$('#color').colorpicker({
					"format" : "hex"
				});
			});

			var dateFields = [ "#startDate", "#endDate", "#refuelDate",
					"#maintenanceDate", "#purchaseDate", "#changeDate" ];
			var dateTimeFields = [ "#routeDatetime" ];

			dateFields.forEach(renderDatepicker);
			dateTimeFields.forEach(renderDateTimePicker);

			function renderDatepicker(data, index) {
				$(data).datetimepicker({
					format : "YYYY-MM-DD"
				});
			}

			function renderDateTimePicker(data, index) {
				$(data).datetimepicker({
					format : "YYYY-MM-DD HH:mm"
				});
			}

			$('#confirm-archive').on(
					'show.bs.modal',
					function(e) {
						$(this).find('.btn-ok').attr('href',
								$(e.relatedTarget).data('href'));
					});

			$('#confirm-delete').on(
					'show.bs.modal',
					function(e) {
						$(this).find('.btn-ok').attr('href',
								$(e.relatedTarget).data('href'));
					});

			$(function() {
				$('[data-toggle="tooltip"]').tooltip()
			});

			// FIXME add user id from data-field on canvas
			$.ajax({
				url : "./ajax?m=afs",
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

					var priceChart = $("#priceChart");
					var barGraph = new Chart(priceChart, {
						type : 'line',
						data : chartdata,
						options: {
							legend: {
								display: false
							}
						},
					});
				},
				error : function(data) {
				}
			});
			
			$.ajax({
				url : "./ajax?m=aap",
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

					var amountChart = $("#amountChart");
					var barGraph = new Chart(amountChart, {
						type : 'line',
						data : chartdata,
						options: {
							legend: {
								display: false
							}
						},
					});
				},
				error : function(data) {
				}
			});

			// Open maps
			var mapObject = $('div#map');
			$('#mapCol').ready(
					function() {
						var lat = mapObject.data('latitude');
						var lon = mapObject.data('longitude');
						var zoom = 17;

						var fromProjection = new OpenLayers.Projection(
								"EPSG:4326");
						var toProjection = new OpenLayers.Projection(
								"EPSG:900913");
						var position = new OpenLayers.LonLat(lon, lat)
								.transform(fromProjection, toProjection);

						map = new OpenLayers.Map("map");
						var mapnik = new OpenLayers.Layer.OSM();
						map.addLayer(mapnik);

						var markers = new OpenLayers.Layer.Markers("Markers");
						map.addLayer(markers);
						markers.addMarker(new OpenLayers.Marker(position));

						map.setCenter(position, zoom);
					});
		});