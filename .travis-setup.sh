#! /bin/bash

set -x
set -e

# Make sure /dev/shm has correct permissions.
ls -l /dev/shm
sudo chmod 1777 /dev/shm
ls -l /dev/shm

uname -a
cat /etc/lsb-release

sudo apt-get update --fix-missing

export VERSION=$(echo $BROWSER | sed -e's/[^-]*-//')
export BROWSER=$(echo $BROWSER | sed -e's/-.*//')

echo BROWSER=$BROWSER
echo VERSION=$VERSION

sudo ln -sf $(which true) $(which xdg-desktop-menu)

case $BROWSER in
Android)
	sudo apt-get install -qq --force-yes \
		libc6:i386 libgcc1:i386 gcc-4.6-base:i386 libstdc++5:i386 \
		libstdc++6:i386 lib32z1 libreadline6-dev:i386 \
		libncurses5-dev:i386
	bash tools/android/setup.sh
	;;

Chrome)
	echo "Getting $VERSION of $BROWSER"
	export CHROME=google-chrome-${VERSION}_current_amd64.deb
	wget https://dl.google.com/linux/direct/$CHROME
	sudo dpkg --install $CHROME || sudo apt-get -f install
	which google-chrome
	ls -l `which google-chrome`
	
	if [ -f /opt/google/chrome/chrome-sandbox ]; then
		export CHROME_SANDBOX=/opt/google/chrome/chrome-sandbox
	else
		export CHROME_SANDBOX=$(ls /opt/google/chrome*/chrome-sandbox)
	fi
	
	# Download a custom chrome-sandbox which works inside OpenVC containers (used on travis).
	sudo rm -f $CHROME_SANDBOX
	sudo wget https://googledrive.com/host/0B5VlNZ_Rvdw6NTJoZDBSVy1ZdkE -O $CHROME_SANDBOX
	sudo chown root:root $CHROME_SANDBOX; sudo chmod 4755 $CHROME_SANDBOX
	sudo md5sum $CHROME_SANDBOX
	
	google-chrome --version
	;;

Firefox)
	sudo rm -f /usr/local/bin/firefox
	case $VERSION in
	beta)
		yes "\n" | sudo add-apt-repository -y ppa:mozillateam/firefox-next
		;;
	aurora)
		yes "\n" | sudo add-apt-repository -y ppa:ubuntu-mozilla-daily/firefox-aurora
		;;
	esac
	sudo apt-get update --fix-missing
	sudo apt-get install firefox
	which firefox
	ls -l `which firefox`
	firefox --version
	;;
esac

R=tools/python/requirements.txt
pip install -r $R --use-mirrors || pip install -r $R