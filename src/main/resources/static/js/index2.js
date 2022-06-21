$(document).ready(function()
{

	$("#fromDate").datepicker({format: "dd/mm/yyyy"}).show();
	$("#toDate").datepicker({format: "dd/mm/yyyy"}).show();
    var tableHide=document.getElementById("auditTable");
    tableHide.style.display="none";
    tableHide.style.visibility="hidden";		
    $(".filterButton").click(function() {
    var fromDate = $('#fromDate').val();
    var toDate = $('#toDate').val();
    var keyword =$('#keyword').val();
    if(fromDate=="")
    {
        var today=new Date("Jan 1, 1970 00:00:00");
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
  		var yyyy = today.getFullYear();
    	if (dd < 10) {
            dd = '0' + dd;
        }
    	if (mm < 10) 
    	{
         	mm = '0' + mm;
    	}		
    	var fromDate = dd + '/' + mm + '/' + yyyy;
    }

    if(toDate=="")
    {
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1;
  		var yyyy = today.getFullYear();
     	if (dd < 10) 
        {
            dd = '0' + dd;
        }
        if (mm < 10) 
        {
    		mm = '0' + mm;
    	}
        var toDate = dd + '/' + mm + '/' + yyyy;
    }
    
    		  
                  $.ajax({
                    type:"GET",
                    url:"/filter/?keyword="+keyword+"&"+"fromDate="+fromDate+"&"+"toDate="+toDate,
                    data: {
                        "keyword":keyword,
                         "fromDate":fromDate,
                         "toDate":toDate,                                 
                     },
                     success: function(res) {
                        if (true) {
              				tableHide.style.display="block";
    						tableHide.style.visibility="visible";
                        } else {
                            alert(res.msg); 
                        }
                    }
                });
               
    });
})