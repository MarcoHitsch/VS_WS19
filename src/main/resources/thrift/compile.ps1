$files = Get-ChildItem .\*.thrift
foreach ($file in $files) {
    thrift -r --gen java $file
}