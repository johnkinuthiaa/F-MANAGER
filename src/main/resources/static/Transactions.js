const main =document.getElementById("main")

async function fetchTransactions(){
    const myHeaders =new Headers()
    myHeaders.append("Content-Type","application/json")
    const response =await fetch("http://localhost:8080/api/v1/transactions/all/transactions",{
        method:"GET",
        headers:myHeaders
        })
    const data =await response.json();
    data?.transactions.map((items)=>{
        const{id,transactionType,senderId,receiverId,amount,transactionId,transactionTime} =items
        main.innerHTML +=`
        <div class="transaction-details" style="display: flex;justify-content: flex-end;padding: 5px;border: 1px solid rgb(0,0,0);align-items: center">
            <div style="width: 30px;justify-content: flex-start">${id}</div>
            <p style="width: 378.25px;padding: 10px">${transactionType}</p>
            <p style="width: 348.65px;padding: 10px">${senderId}</p>
            <p style="width: 348.65px;padding: 10px">${receiverId}</p>
            <p style="width: 34.55px;padding: 10px">${amount}</p>
            <p style="width: 343.33px;padding: 10px">${transactionId}</p>
            <p style="width: 174.8px;padding: 10px">${transactionTime}</p>
        </div>
    `
    }).join(" ")

}
fetchTransactions().then(r=> console.log("loaded data"))