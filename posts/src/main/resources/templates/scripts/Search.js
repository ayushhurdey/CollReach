
let searchBox = "";
let searchItems = "";
let ALL_SKILLS = [];
let finalSkills = [];

$(document).ready(function(){
    let searchBox = document.getElementById("overlay-search-box");
    //let searchBtn = document.getElementById("search-btn");
    let searchItems = document.getElementById('search-items');

    //searchBtn.addEventListener("click", search);

    
    fetch("http://localhost:8082/user/get-all-skills", {
                method: "GET",
                headers: {
                Authorization: localStorage.getItem("auth"),
                }
            })
                .then((response) => response.text())
                .then((ts) => {
                    let skills = JSON.parse(ts);
                    console.log("Skills-> " + skills);
                    skillsList = document.getElementById('skills-list');
                    for(skillKey in skills){
                        ALL_SKILLS.push(skillKey.toLowerCase());
                        var option = document.createElement("OPTION");
                        option.setAttribute("value", skillKey.toLowerCase());
                        option.setAttribute("skillId", skills[skillKey]);
                        var text = document.createTextNode(skillKey.toString());
                        option.appendChild(text);
                        skillsList.appendChild(option);
                    };
                    
                    console.log(ALL_SKILLS);

                    searchBox.addEventListener('input', function(){
                    skills = searchBox.value.trim().split(',');
                    found = [];
                    let current;
                    for(let i = 0; i < ALL_SKILLS.length; i++){
                        current = skills[skills.length -1].toLowerCase();
                        console.log(current);
                        if(current !== "" && ALL_SKILLS[i].toLowerCase().startsWith(current)){
                        found.push(ALL_SKILLS[i]);
                        }
                    }

                    console.log("FOUND: ", found);
                    console.log(finalSkills.includes(current), " " , ALL_SKILLS.includes(current));
                    if(!finalSkills.includes(current) && ALL_SKILLS.includes(current)){
                        finalSkills.push(current);
                        addSpan(searchItems, current);
                        if(searchItems.value === "")
                        searchItems.value = searchItems.value + current;
                        else
                        searchItems.value = searchItems.value + ("," + current);
                        searchBox.value = "";
                    }
                    console.log("final skills ", finalSkills);
                    found = [];
                    });

                });


                    
    /* --------------Overlay Effect functions---------*/
    //document.getElementById('search-btn').addEventListener("click", on);
    document.getElementById('overlay-close').addEventListener("click", off);
    document.getElementById('search-request-btn').addEventListener("click", search);
});


function addSpan(div,value){
    let newDiv = document.createElement('div');
    let cross = document.createElement('div'); 
    newDiv.setAttribute("class", "skill-item");
    cross.setAttribute("class", "skill-item-cross");
    cross.setAttribute('onclick','removeskill(this)');
    cross.innerHTML = "   &#10005;";
    newDiv.innerHTML = value;
    newDiv.appendChild(cross);
    div.appendChild(newDiv);

    cross.addEventListener('click', function(){
        this.parentElement.style.display = 'none';
        let value = this.parentElement.textContent;
        value = value.slice( -value.length, -2).trim();
        console.log(value);
        finalSkills =  finalSkills.filter(item => item !== value);
        console.log("-> final", finalSkills);
    });
}




// function on() {
//   //document.getElementById("search-overlay").style.display = "block";
// }

function off() {
    // document.getElementById("search-overlay").style.display = "none";
    document.querySelector(".search-o").classList.add("hide");
}

function search(){
    console.log("Searching...");
    let searchOption = document.getElementById('search-options').value;
    let itemsOutput = document.getElementById('search-results-display-area');
    let searchQuery = document.getElementById('overlay-search-box').value.trim();
    let searchOutputTemplate = document.querySelector('#search-output-display').innerHTML;
    let skillsListOutputTemplate = document.querySelector('#skills-list-output-display').innerHTML;

    if(searchOption.localeCompare("search-by-name") === 0){
    if(searchQuery == "" || searchQuery == null)
        return;

    fetch("http://localhost:8082/user/search-users-by-name", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            Authorization: localStorage.getItem("auth"),
        },
        body: JSON.stringify({
            "users": searchQuery
            })
        })
        .then((response) => {
            console.log(response); 
            return response.text()})
        .then((res) => {
            let users = JSON.parse(res).usersByName;
            console.log("users-by-name -> " + users);
            itemsOutput.innerHTML = "";
            
            users.forEach(function(value, index, array){
                let name = value.name;
                let username = value.username;
                let profileUrl = "#";
                if(value.profileAccessKey !== "" && value.profileAccessKey !== null)
                profileUrl = "http://localhost:8082/profile/" + value.profileAccessKey;

                let skillsList = "";
                itemsOutput.innerHTML += eval('`' + searchOutputTemplate + '`');

            });
        });
    }
    else{
        let start = Date.now();
        let searchInterval = setInterval(function(){
            if(finalSkills.length !== 0){
                console.log("Searching skills....");
                fetch("http://localhost:8082/user/get-users-from-skills", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: localStorage.getItem("auth"),
                    },
                    body: JSON.stringify({
                        "skills": finalSkills
                    })
                    })
                    .then((response) => response.text())
                    .then((res) => {
                        let usersSkills = JSON.parse(res).usersSkills;
                        console.log("users-by-skills -> " + usersSkills);
                        itemsOutput.innerHTML = "";                               
                        // searchOutputTemplate
                        
                        // for printing purpose only
                        for(key in usersSkills){
                            console.log(key);
                            let name = key;
                            let username = "";
                            let profileUrl = "";
                            let skillsList = "";
                            for(key1 in usersSkills[key]){
                                let eachSearchOutput = "";
                                console.log("    "+key1 + " : "+ usersSkills[key][key1]);
                                if(key1.localeCompare("profileAccessKey") === 0){
                                profileUrl = usersSkills[key][key1];
                                }
                                else if(key1.localeCompare("username") === 0){
                                    username = usersSkills[key][key1];
                                }
                                else if(key1.localeCompare("skillsUpvote") === 0){
                                for(key2 in usersSkills[key][key1]){
                                    console.log("                "+key2 +" : " + usersSkills[key][key1][key2]);
                                    let skill = key2;
                                    let skillUpvotes = usersSkills[key][key1][key2];
                                    skillsList += eval('`' + skillsListOutputTemplate + '`');
                                }
                                } 
                            }
                            itemsOutput.innerHTML += eval('`' + searchOutputTemplate + '`');
                        }
                        
                    });
                clearInterval(searchInterval);
                }
                if((Date.now() - start)/1000 > 10){
                    clearInterval(searchInterval);
                    console.log("Search time out!!");
                }

        }, 100);    
    }
} // search function ends


function addEventToMessages(searchProfileMessageButton){
    message();
    let username = searchProfileMessageButton.dataset.contactUsername;
    let name = searchProfileMessageButton.dataset.contactName;
    addContact(name , "", username);
}

function searchstudent(){
    document.querySelector(".search-o").classList.toggle('hide');
  }

  function closeBtn(elem){
        elem.parentElement.setAttribute('hidden',"");
  }