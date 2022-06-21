$(function()
{
	$(".scheduleButton").click(function() {
		
		
				
    			var jobId = $(this).parent().data("id");
    			$("#jobId").val(jobId);
    			var parameter=$("#parameter_"+jobId).text();
    			console.log(parameter);
    			$("#edit_name").val($("#name_"+jobId).text());
    			$("#edit_id").val(jobId);
    			$("#edit_cron").val($("#cron_"+jobId).text());
    			$("#edit_class").val($("#class_"+jobId).text());
    			$("#edit_status").val($("#status_"+jobId).text());
    			$("#edit_desc").val($("#desc_"+jobId).text());
    			$('#edit_class').attr("readonly","readonly");
    			$('#edit_name').attr("readonly","readonly"); 
    			$('#edit_group').attr("readonly","readonly");
    			$('#edit_desc').attr("readonly","readonly");	
    				
    			$("#myModalLabel").html("Schedule "+$("#name_"+jobId).text() );
    			$("#myModal").modal("show");
    });
    
    $("#save").click(
	    function() {
	    	$.ajax({
	    	url:"/scheduleInstanceOfJob",
	    	type:"POST",
	    	data:  $('#mainForm').serialize(),
	    	
	            success: function(res) {
	            console.log("hello");		
	            	if (true) {
	                	location.reload();
	                } else {
	                	alert("failed"); 
	                }
	            }
	    	}); 
        });
});

 $(".deleteButton").click(
        function() {
            var jobId = $(this).parent().data("id");
            console.log("in delete ajax");
            $.ajax({
                url: "/removeJob",
                type: "POST",
                data: {
                    "jobName": $("#name_"+jobId).text()
                },
                success: function(res) {
                    if (true) {
                        location.reload();
                    } else {
                        alert(res.msg); 
                    }
                }
            });
        });
    
