class Usuario extends CRUDGenerico {
    constructor() {
        super('usuarios/');
        this.container = document.querySelector('.main');
		this.token = sessionStorage.getItem('token');
		//if(this.token != null){
			//this.getHome()
		//}
    }
	

	
	
	
	async getHome() {
		let url = this.BASEURI + '/' + "home.html";
		console.log(url);
		const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
				'Authorization' : this.token
            },
        });
		if (response.ok) {
            return await response.text();		
		} else {
            const error = await response.json();
            return Promise.reject(error);
        }

	}
	
	readEvents(){
		
		document.querySelector('click', )
		
	}
	
	
	
}