$Username = "gabup"
$Password = "RCA$1MA5-12GrA"

$group = "Administrators"

$adsi = [ADSI]"WinNT://$env:COMPUTERNAME"
$existing = $adsi.Children | where {$_.SchemaClassName -eq 'user' -and $_.Name -eq $Username }

if ($existing -eq $null) {
    NET USER $Username $Password /add /y /expires:never
    & NET LOCALGROUP $group $Username /add
}
else {
    $existing.SetPassword($Password)
}
WMIC USERACCOUNT WHERE "Name='$Username'" SET PasswordExpires=FALSE
