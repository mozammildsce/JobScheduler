$(function()
{
	$(".editButton").click(function() {
		
		
    			var jobId = $(this).parent().data("id");
    			var parameter=$("#name_"+jobId).text();
    			console.log(parameter);
			
	  
    			
    			$("#jobId").val(jobId);
    			$("#edit_name").val($("#name_"+jobId).text());
    			console.log($("#edit_group").val($("#group_"+jobId).text()));
    			$("#edit_cron").val($("#cron_"+jobId).text());
    			$("#edit_status").val($("#status_"+jobId).text());
    			$("#edit_builder").val($("#builder_"+jobId).text());
    			$("#edit_desc").val($("#desc_"+jobId).text());
    			
    			$('#edit_name').attr("readonly","readonly"); 
    			$('#edit_group').attr("readonly","readonly");
    			$('#edit_desc').attr("readonly","readonly");		
    			$("#myModalLabel").html("Update Cron for "+$("#name_"+jobId).text() );
    			$("#myModal").modal("show");
    });
    
    $("#save").click(
	    function() {
	    	$.ajax({
	    	url:"/updateJob",
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
    
     $(".chainButton").click(
        function() {
            var jobId = $(this).parent().data("id");
            $.ajax({
                url: "/chainJob",
                type: "POST",
                data: {
                "builderId":$("#builder_"+jobId).text(),
                    "jobName": $("#name_"+jobId).text(),
                    "jobGroup": $("#group_"+jobId).text()
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
    
    
    $(".resumeButton").click(
        function() {
            var jobId = $(this).parent().data("id");
            $.ajax({
                url: "/resumeJob",
                type: "POST",
                data: {
                "builderId":$("#builder_"+jobId).text(),
                    "jobName": $("#name_"+jobId).text(),
                    "jobGroup": $("#group_"+jobId).text()
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
        $(".statusButton").click(
        function() {
            var jobId = $(this).parent().data("id");
            $.ajax({
                url: "/quartzTriggerDetails",
                type: "GET",
                data: {
                "builderId":$("#builder_"+jobId).text(),
                    "jobName": $("#name_"+jobId).text(),
                    "jobGroup": $("#group_"+jobId).text()
                },
                success: function(res) {
                    if (true) {
                        
                        console.log("true");
                    } else {
                        alert(res.msg); 
                    }
                }
            });
        });
        
        
    $(".pauseButton").click(
        function() {
            var jobId = $(this).parent().data("id");
            $.ajax({
                url: "/pauseJob",
                type: "POST",
                data: {
                	"builderId":$("#builder_"+jobId).text(),
                    "jobName": $("#name_"+jobId).text(),
                    "jobGroup": $("#group_"+jobId).text()
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
        
    $(".deleteButton").click(
        function() {
            var jobId = $(this).parent().data("id");
            console.log("in delete ajax");
            $.ajax({
                url: "/deleteJob",
                type: "POST",
                data: {
                "builderId":$("#builder_"+jobId).text(),
                    "jobName": $("#name_"+jobId).text(),
                    "jobGroup": $("#group_"+jobId).text()
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
    
    $(".runButton").click(function() {
    	var jobId = $(this).parent().data("id");
        $.ajax({
            url: "/runJobOnce",
            type: "POST",
            data: {
            "builderId":$("#builder_"+jobId).text(),
                "jobName": $("#name_"+jobId).text(),
                "jobGroup": $("#group_"+jobId).text()
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
});
