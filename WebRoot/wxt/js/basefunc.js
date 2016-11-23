function getAbsLeft(obj){
	var l=obj.offsetLeft; 
	while(obj.offsetParent != null){
		obj = obj.offsetParent;
		l += obj.offsetLeft;
	} 
	return l;
}
