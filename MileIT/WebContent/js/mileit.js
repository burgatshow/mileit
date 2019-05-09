$(document).ready(
		function() {
			$(function() {
				$('#color').colorpicker({
					"format" : "hex"
				});
			});

			var dateFields = [ "#startDate", "#endDate", "#maintenanceDate",
					"#purchaseDate", "#changeDate" ];
			var dateTimeFields = [ "#routeDatetime", "#refuelDate" ];

			var dateTimeFieldsWithSeconds = [ "#expirationDate" ];

			dateFields.forEach(renderDatepicker);
			dateTimeFields.forEach(renderDateTimePicker);
			dateTimeFieldsWithSeconds.forEach(renderDateTimePickerWithSeconds);

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

			function renderDateTimePickerWithSeconds(data, index) {
				$(data).datetimepicker({
					format : "YYYY-MM-DD HH:mm:ss"
				});
			}

			$('#confirm-archive').on(
					'show.bs.modal',
					function(e) {
						$(this).find('.btn-ok').attr('href',
								$(e.relatedTarget).data('href'));
					});

			$('#confirm-activate').on(
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
		});